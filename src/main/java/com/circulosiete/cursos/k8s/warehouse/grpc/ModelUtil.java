package com.circulosiete.cursos.k8s.warehouse.grpc;

import com.circulosiete.cursos.k8s.ProductModel;
import com.circulosiete.cursos.k8s.ProductRequest;
import com.circulosiete.cursos.k8s.ProductResponse;
import com.circulosiete.cursos.k8s.Timestamp;
import com.circulosiete.cursos.k8s.warehouse.model.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class ModelUtil {
  public static Product from(ProductRequest request) {
    return Product.builder()
      .description(request.getDescripcion())
      .name(request.getNombre())
      .price(new BigDecimal(request.getPrecio()))
      .build();
  }

  public static Product from(ProductModel request) {
    return Product.builder()
      .description(request.getDescripcion())
      .name(request.getNombre())
      .price(new BigDecimal(request.getPrecio()))
      .build();
  }

  public static Product from(ProductResponse request) {
    return from(request.getProduct());
  }

  public static ProductResponse from(Optional<Product> found) {

    ProductResponse.Builder builderResponse = ProductResponse.newBuilder();

    found.ifPresent(product -> {
      ProductModel productModel = ProductResponse.newBuilder().getProductBuilder()
        .setNombre(product.getName())
        .setId(product.getId())
        .setDescripcion(product.getDescription())
        .setPrecio(product.getPrice().toString())
        .setCreatedAt(from(product.getCreatedDate()))
        .setModifiedDate(from(product.getModifiedDate()))
        .build();

      builderResponse.setProduct(productModel);
    });

    return builderResponse.build();
  }

  public static Timestamp from(Date date) {
    long millis = date.getTime();
    return Timestamp.newBuilder()
      .setSeconds(millis / 1000)
      .setNanos((int) ((millis % 1000) * 1000000))
      .build();
  }
}
