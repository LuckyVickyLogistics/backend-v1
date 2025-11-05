package com.luckylogistics.product.application.service;

import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.entity.ProductStatus;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.domain.vo.Quantity;
import com.luckylogistics.product.presentation.dto.ProductRequest;
import com.luckylogistics.product.presentation.dto.ProductResponse;
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
                .orElseThrow( () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));


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
                .orElseThrow(() -> new IllegalArgumentException("상품 조회에 실패했습니다."));
    }

    //전체 조회!
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    //삭제 처리
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        product.delete();
    }

    @Transactional
    public void rollbackDeleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        product.rollbackDelete();
    }

}
