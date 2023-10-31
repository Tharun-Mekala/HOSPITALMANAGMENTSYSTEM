package com.pro.hms.entity;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private Integer floor;
	private Integer totalBeds;
	private Integer occpuiedBeds;
	private Integer doctors;
	private Long bed[] = new Long[30];

	public Department(Long id, String name, int floor, int totalBeds, int occpuiedBeds, Long[] bed) {
		super();
		this.id = id;
		this.name = name;
		this.floor = floor;
		this.totalBeds = totalBeds;
		this.occpuiedBeds = occpuiedBeds;
		this.bed = bed;
		Arrays.fill(bed, 0);
	}
	public Department() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getTotalBeds() {
		return totalBeds;
	}
	public void setTotalBeds(int totalBeds) {
		this.totalBeds = totalBeds;
	}
	public int getOccpuiedBeds() {
		return occpuiedBeds;
	}
	public void setOccpuiedBeds(int occpuiedBeds) {
		this.occpuiedBeds = occpuiedBeds;
	}
	public Long getBed(int i) {
		return bed[i];
	}
	public int setBed(Long aId) {
		if(bed==null)
		{
			bed=new Long[30];
			Arrays.fill(bed, 0L);
		}
		for(int i=1;i<=30;i++)
		{
			if(bed[i]==0)
			{
				bed[i]=aId;
				return i;
			}
		}
		return -1;
	}
	public void removeBed(int idx,Long aId)
	{
		bed[idx]=(long)0;
	}
	public Integer getDoctors() {
		return doctors;
	}
	public void setDoctors(Integer doctors) {
		this.doctors = doctors;
	}
	
	
}
