package com.luckylogistics.product.application.service;

import com.luckylogistics.product.application.dto.MinusRequest;
import com.luckylogistics.product.application.dto.PlusRequest;
import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.product.common.exception.BusinessException;
import com.luckylogistics.product.common.exception.ExceptionCode;
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
    public ProductResponse createProduct(ProductRequest productRequest) {
        //허브 ID와 업체 ID를 체크한다.
        hubService.isHubExists(productRequest.hubId());
        companyService.isCompanyExists(productRequest.companyId());

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
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        hubService.isHubExists(productRequest.hubId());
        companyService.isCompanyExists(productRequest.companyId());

        Product product = productRepository.findById(productId)
                .orElseThrow( () -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));


        product.update(
                productRequest.productName(),
                productRequest.price(),
                productRequest.totalQuantity(),
                new Quantity(productRequest.totalQuantity()));

        return ProductResponse.from(product);
    }

    //단건 조회 서비스
    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_READ_FAIL));
    }

    //전체조회 및 검색 서비스
    public List<ProductResponse> allOrSearchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllProducts();
        }
        return searchProductsByName(keyword);
    }


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

    @Transactional
    //삭제 처리
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));

        product.delete();
    }
    //롤백
    @Transactional
    public void rollbackDeleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));

        product.rollbackDelete();
    }

    //비활성
    @Transactional
    public void hiddenProducts(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));
        product.hidden();
    }

    //수량 감소
    @Transactional
    public void minusProducts(UUID productId, MinusRequest minusRequest) {
        //id 찾기
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));
        product.minusQuantity(minusRequest.amount());
    }
    //수량 추가
    @Transactional
    public void plusProducts(UUID productId, PlusRequest plusRequest) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new BusinessException(ExceptionCode.PRODUCT_CANNOT_FIND));
        product.plusQuantity(plusRequest.amount());
    }

}
