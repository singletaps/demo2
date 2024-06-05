package com.example.demo.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // 可以在这里定义自定义查询方法，比如根据发票号查找
    Invoice findByInvoiceNumber(String invoiceNumber);
}
