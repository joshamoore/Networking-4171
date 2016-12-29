
public class RTNode {
	private String mask;
	private String dest;
	private String nextHop;
	private String flag;
	private String rtInterface;
	public RTNode(String mask, String dest, String nextHop, String flag, String rtInterface) {
		this.mask = mask;
		this.dest = dest;
		this.nextHop = nextHop;
		this.flag = flag;
		this.rtInterface = rtInterface;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getNextHop() {
		return nextHop;
	}
	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getRtInterface() {
		return rtInterface;
	}
	public void setRtInterface(String rtInterface) {
		this.rtInterface = rtInterface;
	}
}
