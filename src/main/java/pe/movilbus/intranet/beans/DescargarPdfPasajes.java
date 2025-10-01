package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class DescargarPdfPasajes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String boleto;
	private String pdf;
	
	public DescargarPdfPasajes(){
		super();
	}

	public DescargarPdfPasajes(String boleto, String pdf) {
		super();
		this.boleto = boleto;
		this.pdf = pdf;
	}

	public String getBoleto() {
		return boleto;
	}

	public void setBoleto(String boleto) {
		this.boleto = boleto;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
}
