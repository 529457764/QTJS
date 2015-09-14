package cn.qtone.modules.customer.obj.ht;

public class SearchHtCardInstallFormParam {

	 private String installDateStart;
	 private String installDateEnd;
	 private String htNumber;
	 private int isBSP;
	 private int cardType;//1金税卡 2金税盘 3金税盘(白)
	 private int taxType;
	public String getInstallDateStart() {
		return installDateStart;
	}
	public void setInstallDateStart(String installDateStart) {
		this.installDateStart = installDateStart;
	}
	public String getInstallDateEnd() {
		return installDateEnd;
	}
	public void setInstallDateEnd(String installDateEnd) {
		this.installDateEnd = installDateEnd;
	}
	public String getHtNumber() {
		return htNumber;
	}
	public void setHtNumber(String htNumber) {
		this.htNumber = htNumber;
	}
	public int getIsBSP() {
		return isBSP;
	}
	public void setIsBSP(int isBSP) {
		this.isBSP = isBSP;
	}
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public int getTaxType() {
		return taxType;
	}
	public void setTaxType(int taxType) {
		this.taxType = taxType;
	}
}
