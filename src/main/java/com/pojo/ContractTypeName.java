package com.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contract_type_name")
public class ContractTypeName implements Serializable {

  private Integer orderIndex; // 
  private String id; // 
  private String contractTypeCode; //合同类型编码 
  private String contractName; //排序 
  private Date updateDate; // 
  private Date createDate; //归档日期 

  @Column(name= "order_index",length= 11)
  public Integer getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

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

  @Column(name= "contract_name",length= 64)
  public String getContractName() {
    return contractName;
  }

  public void setContractName(String contractName) {
    this.contractName = contractName;
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
