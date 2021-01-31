package com.spring.domain;

public class TransmissionSummary {
	
	private String id;
	private int recordcount;
	private int qtysum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRecordcount() {
		return recordcount;
	}
	public void setRecordCount(int recordcount) {
		this.recordcount = recordcount;
	}
	public int getQtysum() {
		return qtysum;
	}
	public void setQtysum(int qtysum) {
		this.qtysum = qtysum;
	}

	@Override
    public String toString() {
        return "TransmissionSummary{" +
                ", id='" + id + '\'' +
                ", recordcount=" + recordcount +
                ", qtysum=" + qtysum +
                '}';
    }
	

}
