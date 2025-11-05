package com.luckylogistics.product.application.service;

import com.luckylogistics.product.application.external.CompanyService;
import com.luckylogistics.product.application.external.HubService;
import com.luckylogistics.product.domain.entity.Product;
import com.luckylogistics.product.domain.repository.ProductRepository;
import com.luckylogistics.product.domain.vo.Quantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final HubService hubService;
    private final CompanyService companyService;

    //상품 생성
    public Product createProduct(String productName, UUID companyId, UUID hubId, int price, int totalQuantity, Integer initialQuantity ) {
        //허브 ID와 업체 ID를 체크한다.
        hubService.isHubExists(hubId);
        companyService.isCompanyExists(companyId);

        //초기 수량 세팅, null값 들어오면 0을 리턴( Wrapper 클래스라 null값이 가능) , 아니면 그냥 initialQuantity 값 사용하기
        Quantity quantity = new Quantity(initialQuantity == null ? 0 : initialQuantity); //
        Product product = Product.create(productName, companyId, hubId, quantity, totalQuantity, price);
        return productRepository.save(product);

    }



}
