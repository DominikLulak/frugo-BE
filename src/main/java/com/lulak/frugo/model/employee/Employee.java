package com.lulak.frugo.model.employee;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "employee_number", nullable = false, unique = true)
    private String employeeNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "termination_date")
    private LocalDate terminationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_position_id", nullable = false)
    private JobPosition jobPosition;

    @Column(name = "system_username", nullable = false, unique = true)
    private String systemUsername;

    public Employee(){ }

    public Integer getId() { return id; }

    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }

    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getEmployeeNumber(){ return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber){ this.employeeNumber = employeeNumber; }

    public String getAddress(){ return address; }
    public void setAddress(String address){ this.address = address; }

    public String getCity(){ return city; }
    public void setCity(String city){ this.city = city; }

    public String getPostalCode(){ return postalCode; }
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

    public Shift getShift(){ return shift; }
    public void setShift(Shift shift){ this.shift = shift; }

    public LocalDate getBirthDate(){ return birthDate; }
    public void setBirthDate(LocalDate birthDate){ this.birthDate = birthDate; }

    public LocalDate getHireDate(){ return hireDate; }
    public void setHireDate(LocalDate hireDate){ this.hireDate = hireDate; }

    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public boolean isActive(){ return active; }
    public void setActive(boolean active){ this.active = active; }

    public LocalDate getTerminationDate(){ return terminationDate; }
    public void setTerminationDate(LocalDate terminationDate){ this.terminationDate = terminationDate; }

    public JobPosition getJobPosition(){ return jobPosition; }
    public void setJobPosition(JobPosition jobPosition){ this.jobPosition = jobPosition; }

    public String getSystemUsername(){ return systemUsername; }
    public void setSystemUsername(String systemUsername){ this.systemUsername = systemUsername; }
}
