package com.luckylogistics.product.application.service;

import com.luckylogistics.product.application.dto.MinusRequest;
import com.luckylogistics.product.application.dto.PlusRequest;
import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.common.enums.UserRole;
import com.luckylogistics.product.common.exception.BusinessException;
import com.luckylogistics.product.common.exception.ErrorCode;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.domain.vo.Quantity;
import com.luckylogistics.product.application.dto.ProductRequest;
import com.luckylogistics.product.application.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final HubService hubService;
    private final CompanyService companyService;

    //상품 생성
    @Transactional
    //request에 이미 정보가 있어서 갖고온다.
    public ProductResponse createProduct(ProductRequest productRequest, Long currentUserId, UserRole currentUserRole) {
        //허브 ID와 업체 ID를 체크한다.
        //존재 여부 체크
        hubService.getHub(productRequest.hubId());
        companyService.getCompany(productRequest.companyId());

        //권한 체크
        //마스터 의 경우 getHubByUserId 체크 불가능 USER_ID -> null, 존재하지 않는다로 나올거같아서
        //Role 먼저 체크를 하고, Master일 경우는 그냥 패스,


        // 허브 관리자 , 업체 관리자 본인확인 필요
        //업체 -> user_id 가 들어가있어서
        //hub -> currentUserId 를 getHubByUserId 를 쓰면 HUBID 가 나옴 -> 위에서 사용한 productRequest.hubID();

        if (currentUserRole.isHubManager()) {
            if (!productRequest.hubId().equals(hubService.getHubByUserId(currentUserId).hubId())) {
                throw new BusinessException(ErrorCode.PRODUCT_HUB_MANAGER_ERROR);
            }
        }
        if(currentUserRole.isCompanyManager()){
            if (!productRequest.hubId().equals(companyService.getCompany(productRequest.companyId()).hubId())) {
                throw new BusinessException(ErrorCode.PRODCUT_COMP_MANAGER_ERROR);
            }
        }
        if(currentUserRole.isDeliveryManager()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        //초기 수량 세팅, null값 들어오면 0을 리턴( Wrapper 클래스라 null값이 가능) , 아니면 그냥 initialQuantity 값 사용하기
        Quantity quantity = new Quantity(productRequest.initialQuantity() == null ? 0 : productRequest.initialQuantity());
        //request에서 어떤 값을 사용할지 명시함
        Product product = Product.create(
                productRequest.productName(),
                productRequest.companyId(),
                productRequest.hubId(),
                quantity,
                productRequest.totalQuantity(),
                productRequest.price());
        productRepository.save(product);

        return ProductResponse.from(product);
    }

    //상품 정보 수정
    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest, Long currentUserId, UserRole currentUserRole) {
        hubService.getHub(productRequest.hubId());
        companyService.getCompany(productRequest.companyId());

        Product product = productRepository.findById(productId)
                .orElseThrow( () -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));

        if (currentUserRole.isHubManager()) {
            if (!productRequest.hubId().equals(hubService.getHubByUserId(currentUserId).hubId())) {
                throw new BusinessException(ErrorCode.PRODUCT_HUB_MANAGER_ERROR);
            }
        }

        if(currentUserRole.isDeliveryManager() || currentUserRole.isCompanyManager()) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }


        product.update(
                productRequest.productName(),
                productRequest.price(),
                productRequest.totalQuantity(),
                new Quantity(productRequest.totalQuantity()));

        return ProductResponse.from(product);
    }

    //단건 조회 서비스
    public Product getProduct(UUID productId,Long currentUserId, UserRole currentUserRole) {
        Product product =  productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_READ_FAIL));

        if(currentUserRole.isMaster() ) {
            return product;
        }

        //허브 관리자 + 배송 관리자 (자기 허브꺼만 볼 수 있음)
        if(currentUserRole.isHubManager() || currentUserRole.isDeliveryManager()){
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {  //가진 hubId와 조회한 hubId가 다르다면,
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            return productRepository.findByProductIdAndHubId(productId, userHubId).
                    orElseThrow( () -> new BusinessException(ErrorCode.PRODUCT_READ_FAIL));
        }

        //userId company랑 맞춰줘야 함
        if (currentUserRole.isCompanyManager()) {
            UUID userCompanyId  = companyService.getCompanyUserId(currentUserId).companyId();
            if ((!product.getCompanyId().equals(userCompanyId))) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
             return productRepository.findByProductIdAndCompanyId(productId, userCompanyId).
                    orElseThrow( () -> new BusinessException(ErrorCode.PRODUCT_READ_FAIL));
        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    //전체조회 및 검색 서비스
    public List<ProductResponse> allOrSearchProducts(String keyword,Long currentUserId, UserRole currentUserRole) {
      boolean hasKeyword = (keyword != null && !keyword.isBlank());

      //Master
        if (currentUserRole.isMaster()){
            if(!hasKeyword) {
                return getAllProducts();
            }
            return searchProductsByName(keyword);
        }

        //HUB,DELIVERY 본인 허브만
        if (currentUserRole.isHubManager() || currentUserRole.isDeliveryManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if(!hasKeyword) {
                return getAllProductsByHub(userHubId);
            }
            return searchProductsByNameAndHub(keyword, userHubId);
        }

        // COMPANY_MANAGER: 본인 업체만
        if (currentUserRole.isCompanyManager()) {
            UUID userCompanyId = companyService.getCompanyUserId(currentUserId).companyId();
            if (!hasKeyword) {
                return getAllProductsByCompany(userCompanyId);
            }
            return searchProductsByNameAndCompany(keyword, userCompanyId);
        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

//    //FeignClient용
//    public Boolean checkProduct(UUID productId) {
//        return productRepository.findById(productId).isPresent();
//    }

    //전체 조회!
    private List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductResponse::from).toList();

    }

    //검색
    private List<ProductResponse> searchProductsByName(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        return products.stream().map(ProductResponse::from).toList();
    }


    // 허브별 전체조회
    private List<ProductResponse> getAllProductsByHub(UUID hubId) {
        List<Product> products = productRepository.findByHubId(hubId);
        return products.stream().map(ProductResponse::from).toList();
    }

    // 허브별 검색
    private List<ProductResponse> searchProductsByNameAndHub(String keyword, UUID hubId) {
        List<Product> products = productRepository.findByHubIdAndProductNameContainingIgnoreCase(hubId, keyword);
        return products.stream().map(ProductResponse::from).toList();
    }

    // 업체별 전체조회
    private List<ProductResponse> getAllProductsByCompany(UUID companyId) {
        List<Product> products = productRepository.findByCompanyId(companyId);
        return products.stream().map(ProductResponse::from).toList();
    }

    // 업체별 검색
    private List<ProductResponse> searchProductsByNameAndCompany(String keyword, UUID companyId) {
        List<Product> products = productRepository.findByCompanyIdAndProductNameContainingIgnoreCase(companyId, keyword);
        return products.stream().map(ProductResponse::from).toList();
    }

    @Transactional
    //삭제 처리
    public void deleteProduct(UUID productId,Long currentUserId, UserRole currentUserRole) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));

        if (currentUserRole.isMaster()) {
            product.delete();
            return;
        }

        if (currentUserRole.isHubManager() ) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            product.delete();
            return;

        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    //롤백
    @Transactional
    public void rollbackDeleteProduct(UUID productId,Long currentUserId, UserRole currentUserRole) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));

        if (currentUserRole.isMaster()) {
            product.rollbackDelete();
            return;
        }

        if (currentUserRole.isHubManager() ) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            product.rollbackDelete();
            return;

        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    //비활성
    @Transactional
    public void hiddenProducts(UUID productId,Long currentUserId, UserRole currentUserRole) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));

        if (currentUserRole.isMaster()) {
            product.hidden();
            return;
        }

        if (currentUserRole.isHubManager() ) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            product.hidden();
            return;

        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    //수량 감소
    @Transactional
    public void minusProducts(UUID productId, MinusRequest minusRequest, Long currentUserId, UserRole currentUserRole) {
        //id 찾기
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));
        if (currentUserRole.isMaster()) {
            product.minusQuantity(minusRequest.amount());
            return;
        }

        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            product.minusQuantity(minusRequest.amount());
            return;
        }
        throw new BusinessException(ErrorCode.FORBIDDEN);

    }
    //수량 추가
    @Transactional
    public void plusProducts(UUID productId, PlusRequest plusRequest, Long currentUserId, UserRole currentUserRole) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CANNOT_FIND));

        if (currentUserRole.isMaster()) {
            product.minusQuantity(plusRequest.amount());
            return;
        }

        if (currentUserRole.isHubManager()) {
            UUID userHubId = hubService.getHubByUserId(currentUserId).hubId();
            if (!product.getHubId().equals(userHubId)) {
                throw new BusinessException(ErrorCode.FORBIDDEN);
            }
            product.minusQuantity(plusRequest.amount());
            return;
        }
        throw new BusinessException(ErrorCode.FORBIDDEN);
    }

}
