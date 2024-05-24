package com.docmall.demo.domain;

import java.util.Date;

/*테이블 컬럼명
 employee_id, emp_name, email, phone_number, hire_date, salary, manager_id, commission_pct, retire_date, department_id, job_id, create_date, update_date
 
 EMPLOYEE_ID   NOT NULL  NUMBER(6)    PRIMARY KEY
EMP_NAME       NOT NULL  VARCHAR2(80) 
EMAIL                    VARCHAR2(50) 
PHONE_NUMBER             VARCHAR2(30) 
HIRE_DATE      NOT NULL  DATE         
SALARY                   NUMBER(8,2)  
MANAGER_ID               NUMBER(6)    
COMMISSION_PCT           NUMBER(2,2)  
RETIRE_DATE              DATE         
DEPARTMENT_ID            NUMBER(6)    
JOB_ID                   VARCHAR2(10) 
CREATE_DATE              DATE         
UPDATE_DATE              DATE
 */

// 용도? Employees 테이블의 데이터를 저장하거나 불어올 때 사용하는 객체.
// 사용자가 입력한 데이터를 EmployeeVO클래스로 객체를 생성하여, 메모리상 저장.
// Employees 테이블의 데이터를 읽어와서 EmployeeVO클래스로 객체를 생성하여, 메모리상 저장.

public class EmoloyeeVO {
	
	private  Integer  employee_id; // PRIMARY KEY 컬럼명을 참조. int, long 기본테이터타입 사용하지않고, Weapper클래스사용을 권장.
	private  String   emp_name;
	private  String   email;
	private  String   phone_number;
	private  Date     hire_date;
	private  double   salary;
	private  int      manager_id;
	private  int      commission_pct;
	private  Date     retire_date;
	private  int      department_id;
	private  String   job_id;
	private  Date     create_date;
	private  Date     update_date;
	
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmplotee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public int getManager_id() {
		return manager_id;
	}
	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}
	public int getCommission_pct() {
		return commission_pct;
	}
	public void setCommission_pct(int commission_pct) {
		this.commission_pct = commission_pct;
	}
	public Date getRetire_date() {
		return retire_date;
	}
	public void setRetire_date(Date retire_date) {
		this.retire_date = retire_date;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	@Override
	public String toString() {
		return "EmoloyeeVO [emplotee_id=" + employee_id + ", emp_name=" + emp_name + ", email=" + email
				+ ", phone_number=" + phone_number + ", hire_date=" + hire_date + ", salary=" + salary + ", manager_id="
				+ manager_id + ", commission_pct=" + commission_pct + ", retire_date=" + retire_date
				+ ", department_id=" + department_id + ", job_id=" + job_id + ", create_date=" + create_date
				+ ", update_date=" + update_date + "]";
	}
	
	
}
