package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class RequestPayBeans implements Serializable{

	private static final long serialVersionUID = 1L;

		private String action;
		private String method;
		private String data_sessiontoken;
		private String data_channel;
		private String data_merchantid;
		private String data_purchasenumber;
		private double data_amount;
		private int data_expirationminutes;
		private String data_timeouturl;
		private String data_merchantlogo;
		private String data_merchantname;		
		private String data_buttonsize;
		private String data_buttoncolor;
		private String data_formbuttoncolor;
		private String data_showamount;
		private String data_cardholdername;
		private String data_cardholderlastname;
		private String data_cardholderemail;
		private String data_usertoken;
		private String data_hidexbutton;
		
		public RequestPayBeans() {
			super();
		}

		public RequestPayBeans(String action, String method, String data_sessiontoken, String data_channel,
				String data_merchantid, String data_purchasenumber, double data_amount, int data_expirationminutes,
				String data_timeouturl, String data_merchantlogo, String data_merchantname, String data_buttonsize,
				String data_buttoncolor, String data_formbuttoncolor, String data_showamount,
				String data_cardholdername, String data_cardholderlastname, String data_cardholderemail,
				String data_usertoken, String data_hidexbutton) {
			super();
			this.action = action;
			this.method = method;
			this.data_sessiontoken = data_sessiontoken;
			this.data_channel = data_channel;
			this.data_merchantid = data_merchantid;
			this.data_purchasenumber = data_purchasenumber;
			this.data_amount = data_amount;
			this.data_expirationminutes = data_expirationminutes;
			this.data_timeouturl = data_timeouturl;
			this.data_merchantlogo = data_merchantlogo;
			this.data_merchantname = data_merchantname;
			this.data_buttonsize = data_buttonsize;
			this.data_buttoncolor = data_buttoncolor;
			this.data_formbuttoncolor = data_formbuttoncolor;
			this.data_showamount = data_showamount;
			this.data_cardholdername = data_cardholdername;
			this.data_cardholderlastname = data_cardholderlastname;
			this.data_cardholderemail = data_cardholderemail;
			this.data_usertoken = data_usertoken;
			this.data_hidexbutton = data_hidexbutton;
		}



		public String getAction() {
			return action;
		}



		public void setAction(String action) {
			this.action = action;
		}



		public String getMethod() {
			return method;
		}



		public void setMethod(String method) {
			this.method = method;
		}



		public String getData_sessiontoken() {
			return data_sessiontoken;
		}



		public void setData_sessiontoken(String data_sessiontoken) {
			this.data_sessiontoken = data_sessiontoken;
		}



		public String getData_channel() {
			return data_channel;
		}



		public void setData_channel(String data_channel) {
			this.data_channel = data_channel;
		}



		public String getData_merchantid() {
			return data_merchantid;
		}



		public void setData_merchantid(String data_merchantid) {
			this.data_merchantid = data_merchantid;
		}



		public String getData_purchasenumber() {
			return data_purchasenumber;
		}



		public void setData_purchasenumber(String data_purchasenumber) {
			this.data_purchasenumber = data_purchasenumber;
		}



		public double getData_amount() {
			return data_amount;
		}



		public void setData_amount(double data_amount) {
			this.data_amount = data_amount;
		}



		public int getData_expirationminutes() {
			return data_expirationminutes;
		}



		public void setData_expirationminutes(int data_expirationminutes) {
			this.data_expirationminutes = data_expirationminutes;
		}



		public String getData_timeouturl() {
			return data_timeouturl;
		}



		public void setData_timeouturl(String data_timeouturl) {
			this.data_timeouturl = data_timeouturl;
		}



		public String getData_merchantlogo() {
			return data_merchantlogo;
		}



		public void setData_merchantlogo(String data_merchantlogo) {
			this.data_merchantlogo = data_merchantlogo;
		}



		public String getData_merchantname() {
			return data_merchantname;
		}



		public void setData_merchantname(String data_merchantname) {
			this.data_merchantname = data_merchantname;
		}



		public String getData_buttonsize() {
			return data_buttonsize;
		}



		public void setData_buttonsize(String data_buttonsize) {
			this.data_buttonsize = data_buttonsize;
		}



		public String getData_buttoncolor() {
			return data_buttoncolor;
		}



		public void setData_buttoncolor(String data_buttoncolor) {
			this.data_buttoncolor = data_buttoncolor;
		}



		public String getData_formbuttoncolor() {
			return data_formbuttoncolor;
		}



		public void setData_formbuttoncolor(String data_formbuttoncolor) {
			this.data_formbuttoncolor = data_formbuttoncolor;
		}



		public String getData_showamount() {
			return data_showamount;
		}



		public void setData_showamount(String data_showamount) {
			this.data_showamount = data_showamount;
		}



		public String getData_cardholdername() {
			return data_cardholdername;
		}



		public void setData_cardholdername(String data_cardholdername) {
			this.data_cardholdername = data_cardholdername;
		}



		public String getData_cardholderlastname() {
			return data_cardholderlastname;
		}



		public void setData_cardholderlastname(String data_cardholderlastname) {
			this.data_cardholderlastname = data_cardholderlastname;
		}



		public String getData_cardholderemail() {
			return data_cardholderemail;
		}



		public void setData_cardholderemail(String data_cardholderemail) {
			this.data_cardholderemail = data_cardholderemail;
		}



		public String getData_usertoken() {
			return data_usertoken;
		}



		public void setData_usertoken(String data_usertoken) {
			this.data_usertoken = data_usertoken;
		}



		public String getData_hidexbutton() {
			return data_hidexbutton;
		}



		public void setData_hidexbutton(String data_hidexbutton) {
			this.data_hidexbutton = data_hidexbutton;
		}

}
