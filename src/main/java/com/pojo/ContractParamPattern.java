package com.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contract_param_pattern")
public class ContractParamPattern implements Serializable {

  private String paramCode; // 
  private Integer id; // 
  private String paramName; // 
  private String type; //1:普通合同;2:订单合同 
  private String pattern; // 
  private Date createDate; // 
  private Date updateDate; // 
  private String matcherType; //匹配类型，1:合同名称;2:合同条形码;3:合同属性 

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

  @Column(name= "type",length= 6)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Column(name= "pattern",length= 255)
  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
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

  @Column(name= "matcher_type",length= 6)
  public String getMatcherType() {
    return matcherType;
  }

  public void setMatcherType(String matcherType) {
    this.matcherType = matcherType;
  }

}
