package com.sec.util;

import java.io.Serializable;
import java.util.ArrayList;


public class PageModel<T> implements Serializable{
	
	private int pageSize = 5;//ÿҳ��ʾ������
	private int pageNo = 1;//��ǰҳ��
	private int totalRecords = 0;//�ܼ�¼��
	private ArrayList<T> data = new ArrayList<T>();//��ǰҳ������
	/*
	 * ��ȡÿҳ��ʾ�ļ�¼������
	 */
	public int getPageSize() {
		return pageSize;
	}
	/*
	 * ����ÿҳ��ʾ��¼������
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/*
	 * ��ȡ��ǰҳ��ҳ��
	 */
	public int getPageNo() {
		return pageNo;
	}
	/*
	 * ���õ�ǰҳ��ҳ��
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/*
	 * ��ȡ��ҳ��
	 */
	public int getTotalpages(){
		int temp = (totalRecords+pageSize-1)/pageSize;
		
		return temp <= 0 ? 1 : temp;
		
		/*����ע�͵�����һ�����ܺ�ҳ���ķ�ʽ
		 
		 if(totalRecords%pageSize==0){
			return totalRecords/pageSize;
			
		}else{
			return totalRecords/pageSize+1;
		}*/
		
	}
	
	/*
	 * ��ȡ��¼����
	 */
	public int getTotalRecords() {
		return this.totalRecords;
	}
	
	/*
	 * ���ü�¼����
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/*
	 * ��ȡ��ǰҳ������
	 */
	public ArrayList<T> getData() {
		return data;
	}
	/*
	 * ���õ�ǰҳ������
	 */
	public void setData(ArrayList<T> date) {
		this.data = date;
	}
	
	/*
	 * ��ȡ��ҳҳ��
	 */
	public int getIndexPageNo(){
		return 1;
	}
	
	/*
	 * ��ȡβҳ
	 */
	public int getLastPageNo(){
		return this.getTotalpages();
	}
	/*
	 * ��ȡ��һҳҳ��
	 */
	public int getPreviousPageNo(){
		if(this.pageNo <= 1){
			return 1;
		}else{
			return this.pageNo-1;
		}
	}
	/*
	 * ��ȡ��һҳҳ��
	 */
	public int getNextPageNo(){
		if(this.pageNo >= this.getTotalpages()){
			return this.getTotalpages();
		}else{
			return this.pageNo+1;
		}
	}
}
