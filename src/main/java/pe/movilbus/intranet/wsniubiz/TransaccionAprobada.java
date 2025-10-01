package pe.movilbus.intranet.wsniubiz;

public class TransaccionAprobada {
	private Header header;
	private Fullfillment fullfillment;
	private Order order;
	private DataMap dataMap;
	public TransaccionAprobada() {
		
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Fullfillment getFullfillment() {
		return fullfillment;
	}
	public void setFullfillment(Fullfillment fullfillment) {
		this.fullfillment = fullfillment;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public DataMap getDataMap() {
		return dataMap;
	}
	public void setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
	}
}
