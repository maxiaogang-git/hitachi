package com.example.hitachi.service;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import com.example.hitachi.mapper.HitachiServiceFeeMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HitachiServiceFeeService {

    @Autowired
    private HitachiServiceFeeMapper hitachiServiceFeeMapper;

    public Map<String,Object> getServiceFeeInfo(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        String send_start_date = (String)params.get("send_start_date");
        String receive_start_date = (String)params.get("receive_start_date");
        if(!StringUtils.isEmpty(send_start_date)){
            params.put("send_start_date",send_start_date.split("-")[0].trim());
            params.put("send_end_date",send_start_date.split("-")[1].trim());
        }
        if(!StringUtils.isEmpty(receive_start_date)){
            params.put("receive_start_date",receive_start_date.split("-")[0].trim());
            params.put("receive_end_date",receive_start_date.split("-")[1].trim());

        }
        int pageNo = ((Integer) params.get("pageNo") - 1) * (Integer) params.get("pageSize");
        params.put("pageNo",pageNo);
        List<HitachiServiceFeeEntity> list = hitachiServiceFeeMapper.getServiceFeeInfo(params);
        int total = hitachiServiceFeeMapper.getDataTotal(params);
        map.put("count",total);
        map.put("data",list);
        return map;

    }

    public int addServiceFee(Map<String,Object> params){
        String uuid= UUID.randomUUID().toString();
        params.put("uuid",uuid);
        Object obj = params.get("is_three_contract");
        if(obj == null){
            params.put("is_three_contract","0");
        }
        int count = hitachiServiceFeeMapper.getServeFeeByContractNO(params);
        if(count>0){
            return -1;
        }
        return hitachiServiceFeeMapper.addServiceFee(params);
//        return hitachiServiceFeeMapper.addServiceFeeChild(params);
    }




    public int batchDeleteServiceFee(List<String> list){
        List<String> contractNoList = hitachiServiceFeeMapper.getServeFeeChildList(list);
        hitachiServiceFeeMapper.deleteServiceFeeChild(contractNoList,"parent");
        return hitachiServiceFeeMapper.batchDeleteServiceFee(list);
    }


    public HitachiServiceFeeEntity getServiceFeeDataByKey(String uuid){
        return hitachiServiceFeeMapper.getServiceFeeDataByKey(uuid);

    }

    public int updateServiceFee(Map<String,Object> params){
        Object obj = params.get("is_three_contract");
        if(obj == null){
            params.put("is_three_contract","0");
        }else{
            params.put("is_three_contract","1");
        }
        hitachiServiceFeeMapper.updateServiceFee(params);
        hitachiServiceFeeMapper.updateChildByParent(params);
        return 1;
    }



    public Map<String,Object> getServiceFeeInfoChild(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        int pageNo = ((Integer) params.get("pageNo") - 1) * (Integer) params.get("pageSize");
        params.put("pageNo",pageNo);
        HitachiServiceFeeEntity obj = hitachiServiceFeeMapper.getServiceFeeDataByKey(params.get("uuid").toString());
        List<HitachiServiceFeeEntity> list = hitachiServiceFeeMapper.getServiceFeeInfoChild(params);
        int total = hitachiServiceFeeMapper.getDataTotalChild(params);
        map.put("count",total);
        map.put("data",list);
        map.put("obj",obj);
        return map;

    }


    /**
     * 修改下面table
     * @param uuid
     * @return
     */
    public HitachiServiceFeeEntity getServiceFeeChildDataByKey(String uuid){
        return hitachiServiceFeeMapper.getServiceFeeChildDataByKey(uuid);

    }


    /**
     * 增加子table信息
     * @param params
     * @return
     */
    public int addServiceFeeChild(Map<String,Object> params){
        String uuid= UUID.randomUUID().toString();
        params.put("uuid",uuid);
        return hitachiServiceFeeMapper.addServiceFeeChild(params);
    }

    /**
     * 修改子table信息
     * @param params
     * @return
     */
    public int updateServiceFeeChild(Map<String,Object> params){
        return hitachiServiceFeeMapper.updateServiceFeeChild(params);
    }


    /**
     * 删除下面table数据
     * @param list
     * @return
     */
    public int deleteServiceFeeChild(List<String> list){
        return hitachiServiceFeeMapper.deleteServiceFeeChild(list,"");
    }


    /**
     * EXCEL下载
     */
    public void exportData(HttpServletResponse response) throws Exception{
        XSSFWorkbook workbook = new XSSFWorkbook();
        String columNames[] = {"合同编号","合同买方","使用单位","责任人","寄总公司日期","收到确认书日期","合同货款总额","是否三方合同","备注"};
        String columNames2[] = {"合同编号","合同买方","合同货款总额","是否三方合同","公司名称","服务费金额","第一笔支付金额","第一笔支付时间","第二笔支付金额","第二笔支付时间","第三笔支付金额","第三笔支付时间","第四笔支付金额","第四笔支付时间","第五笔支付金额","第五笔支付时间","收到确认书日期"};
        Sheet sheet = workbook.createSheet("服务费");
        Sheet sheet2 = workbook.createSheet("服务费支付");
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setFont(titleFont);

        Row titleRow = sheet.createRow(0);
        for(int i=0;i<columNames.length;i++){
            int columnWidth = sheet.getColumnWidth(i) / 256;
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(columNames[i]);
            cell.setCellStyle(titleStyle);
            sheet.setColumnWidth(i,columnWidth * 700);
        }

        Row titleRow2 = sheet2.createRow(0);
        for(int j=0;j<columNames2.length;j++){
            int columnWidth = sheet2.getColumnWidth(j) / 256;
            Cell cell = titleRow2.createCell(j);
            cell.setCellValue(columNames2[j]);
            cell.setCellStyle(titleStyle);
            sheet2.setColumnWidth(j, columnWidth * 500);
        }

        List<HitachiServiceFeeEntity> listData = hitachiServiceFeeMapper.getExportData();
        List<HitachiServiceFeeEntity> childListData = hitachiServiceFeeMapper.getExportChildData();

        for(int i=0;i<listData.size();i++){
            HitachiServiceFeeEntity obj = listData.get(i);
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(obj.getContractNo());
            dataRow.createCell(1).setCellValue(obj.getContractBuyer());
            dataRow.createCell(2).setCellValue(obj.getUseCompany());
            dataRow.createCell(3).setCellValue(obj.getPersonLiable());
            dataRow.createCell(4).setCellValue(obj.getSendCompanyDate());
            dataRow.createCell(5).setCellValue(obj.getGetConfirmDate());
            String contractLoanTotal = obj.getContractLoanTotal();
            if("".equals(contractLoanTotal) || contractLoanTotal == null){
                dataRow.createCell(6).setCellValue("");
            }else{
                dataRow.createCell(6).setCellValue(Double.parseDouble(obj.getContractLoanTotal()));
            }
            dataRow.createCell(7).setCellValue(obj.getIsThreeContract());
            dataRow.createCell(8).setCellValue(obj.getNote());
        }

        for(int j=0;j<childListData.size();j++){
            HitachiServiceFeeEntity obj = childListData.get(j);
            int lastRowNum = sheet2.getLastRowNum();
            Row dataRow = sheet2.createRow(lastRowNum + 1);
            dataRow.createCell(0).setCellValue(obj.getContractNo());
            dataRow.createCell(1).setCellValue(obj.getContractBuyer());

            String contractLoanTotal = obj.getContractLoanTotal();
            if("".equals(contractLoanTotal) || contractLoanTotal == null){
                dataRow.createCell(2).setCellValue("");
            }else{
                dataRow.createCell(2).setCellValue(Double.parseDouble(obj.getContractLoanTotal()));
            }
            dataRow.createCell(3).setCellValue(obj.getIsThreeContract());
            dataRow.createCell(4).setCellValue(obj.getCompanyName());

            String serveFeeCash = obj.getServeFeeCash();
            if("".equals(serveFeeCash) || serveFeeCash == null){
                dataRow.createCell(5).setCellValue("");
            }else{
                dataRow.createCell(5).setCellValue(Double.parseDouble(obj.getServeFeeCash()));
            }

            String firstPayFee = obj.getFirstPayFee();
            if("".equals(firstPayFee) || firstPayFee == null){
                dataRow.createCell(6).setCellValue("");
            }else{
                dataRow.createCell(6).setCellValue(Double.parseDouble(obj.getFirstPayFee()));
            }
            dataRow.createCell(7).setCellValue(obj.getFirstPayDate());

            String secondPayFee = obj.getSecondPayFee();
            if("".equals(secondPayFee) || secondPayFee == null){

                dataRow.createCell(8).setCellValue("");
            }else{
                dataRow.createCell(8).setCellValue(Double.parseDouble(obj.getSecondPayFee()));
            }
            dataRow.createCell(9).setCellValue(obj.getSecondPayDate());

            String thirdPayFee = obj.getThirdPayFee();
            if("".equals(thirdPayFee) || thirdPayFee == null){
                dataRow.createCell(10).setCellValue("");
            }else{
                dataRow.createCell(10).setCellValue(Double.parseDouble(obj.getThirdPayFee()));
            }
            dataRow.createCell(11).setCellValue(obj.getThirdPayDate());

            String fourPayFee = obj.getFourPayFee();
            if("".equals(fourPayFee) || fourPayFee == null){
                dataRow.createCell(12).setCellValue("");
            }else{
                dataRow.createCell(12).setCellValue(Double.parseDouble(obj.getFourPayFee()));
            }
            dataRow.createCell(13).setCellValue(obj.getFourPayDate());

            String fivePayFee  = obj.getFivePayFee();
            if("".equals(fivePayFee) || fivePayFee == null){
                dataRow.createCell(14).setCellValue("");
            }else{
                dataRow.createCell(14).setCellValue(Double.parseDouble(obj.getFivePayFee()));
            }
            dataRow.createCell(15).setCellValue(obj.getFivePayDate());

            dataRow.createCell(16).setCellValue(obj.getGetConfirmDate());
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition","attachment;filename="+ URLEncoder.encode("服务费.xlsx","utf-8"));
        response.setHeader("Access-Control-Expose-Headers","content-Disposition");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }


}
