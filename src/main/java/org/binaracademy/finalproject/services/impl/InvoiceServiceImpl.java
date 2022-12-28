package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binaracademy.finalproject.data.TicketData;
import org.binaracademy.finalproject.dto.Response.OrderResponse;
import org.binaracademy.finalproject.services.InvoiceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final String ERROR_FOUND = "Error found : {}";

    public JasperReport getFile(String file){
        try{
            ClassPathResource classPath = new ClassPathResource("data/"+file+".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(classPath.getInputStream());
            return jasperReport;
        }catch (JRException | IOException e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    public byte[] generateInvoice(OrderResponse order, TicketData ticket, String file) {
        try{
            List<Object> state = new ArrayList<>();
            if (file.equals("Ticket")){
                state.add(ticket);
                JasperReport jasperReport = getFile("Ticket");
                Map<String, Object> parameter = mapTicket(ticket);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JRBeanCollectionDataSource(state));
                log.info("Order invoice has been created");
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }
            JasperReport jasperReport = getFile("Order");
            order.getOrderDetail().forEach(x -> state.add(x));
            Map<String, Object> parameter = mapOrder(order);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JRBeanCollectionDataSource(state));
            log.info("Order invoice has been created");
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch(JRException e){
            log.error(ERROR_FOUND, e);
            return null;
        }
    }

    public Map<String, Object> mapOrder(OrderResponse order){
        try{
            Map<String, Object> parameter = new HashMap<>();
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            parameter.put("EMAIL", order.getEmail());
            parameter.put("DATE", order.getOrderDate().format(dateTime).toString());
            parameter.put("TOTAL_PRICE", toRupiah(order.getTotalPrice()));
            parameter.put("TAX", toRupiah(order.getTax()));
            parameter.put("TOTAL_PAY", toRupiah(order.getTotalPay()));
            return parameter;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    public Map<String, Object> mapTicket(TicketData ticket){
        try{
            ClassPathResource path = new ClassPathResource("image/trueMap.png");
            Map<String, Object> parameter = new HashMap<>();
            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime boarding = ticket.getBoarding().minusMinutes(30);
            parameter.put("GUEST_NAME", ticket.getGuest());
            parameter.put("CATEGORY", ticket.getCategory());
            parameter.put("DEPARTURE", ticket.getDeparture());
            parameter.put("ARRIVAL", ticket.getArrival());
            parameter.put("FLIGHT_DATE", ticket.getDate().format(date).toString());
            parameter.put("BOARDING", boarding.format(time).toString());
            parameter.put("SEAT_NAME", ticket.getSeat());
            parameter.put("IMAGE_MAP", path.getPath());
            return parameter;
        }catch (Exception e){
            log.error(ERROR_FOUND, e);
            return null;
        }
    }

    public String toRupiah(BigDecimal value) {
        BigDecimal displayVal = value.setScale(2, RoundingMode.HALF_EVEN);
        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatRp);
        return toRupiah.format(displayVal);
    }
}
