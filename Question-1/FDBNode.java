
public class FDBNode {
	private String address;
	private int port_out;
	
	public FDBNode(String address, int port_out) {
		this.address = address;
		this.port_out = port_out;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPort_out() {
		return port_out;
	}
	public void setPort_out(int port_out) {
		this.port_out = port_out;
	}
}
