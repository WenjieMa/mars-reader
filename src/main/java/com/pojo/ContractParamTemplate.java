package com.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contract_param_template")
public class ContractParamTemplate implements Serializable {

  private String paramCode; // 
  private Integer id; // 
  private String paramName; // 
  private Integer orderIndex; //排序
  private String templateCode; // 
  private Date createDate; // 
  private Date updateDate; // 
  private String effective; //1:有效;0:无效

  @Column(name= "param_code",length= 64)
  public String getParamCode() {
    return paramCode;
  }

  public void setParamCode(String paramCode) {
    this.paramCode = paramCode;
  }

  @Id 
  @Column(name= "id",length= 64)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name= "param_name",length= 64)
  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  @Column(name= "order_index",length= 64)
  public Integer getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

  @Column(name= "template_code",length= 255)
  public String getTemplateCode() {
    return templateCode;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  @Temporal(value =TemporalType.TIMESTAMP) 
  @Column(name= "create_date",length= 7)
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Temporal(value =TemporalType.TIMESTAMP) 
  @Column(name= "update_date",length= 7)
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  @Column(name= "effective",length= 2)
  public String getEffective() {
    return effective;
  }

  public void setEffective(String effective) {
    this.effective = effective;
  }

}
