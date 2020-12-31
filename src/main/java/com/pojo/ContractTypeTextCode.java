package com.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contract_type_text_code")
public class ContractTypeTextCode implements Serializable {

  private String id; // 
  private String contractTypeCode; //合同类型编码 
  private String contractTextCode; //排序 
  private Date updateDate; // 
  private Date createDate; //归档日期 

  @Id 
  @Column(name= "id",length= 32)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name= "contract_type_code",length= 32)
  public String getContractTypeCode() {
    return contractTypeCode;
  }

  public void setContractTypeCode(String contractTypeCode) {
    this.contractTypeCode = contractTypeCode;
  }

  @Column(name= "contract_text_code",length= 64)
  public String getContractTextCode() {
    return contractTextCode;
  }

  public void setContractTextCode(String contractTextCode) {
    this.contractTextCode = contractTextCode;
  }

  @Temporal(value =TemporalType.TIMESTAMP) 
  @Column(name= "update_date",length= 7)
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  @Temporal(value =TemporalType.TIMESTAMP) 
  @Column(name= "create_date",length= 7)
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

}
