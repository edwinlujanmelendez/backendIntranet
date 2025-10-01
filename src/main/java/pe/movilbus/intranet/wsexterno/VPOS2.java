package pe.movilbus.intranet.wsexterno;

import java.io.Serializable;

public class VPOS2  implements Serializable{

	private static final long serialVersionUID = 1L;
	// Parametros de entrada
		private String acquirerId;
		private String idCommerce;
		private String purchaseOperationNumber;
		private String purchaseAmount;
		private String purchaseCurrencyCode;
		private String commerceMallId;
		private String language;
		private String billingFirstName;
		private String billingLastName;
		private String billingEmail;
		private String billingAddress;
		private String billingZIP;
		private String billingCity;
		private String billingState;
		private String billingCountry;
		private String billingPhone;
		private String shippingFirstName;
		private String shippingLastName;
		private String shippingEmail;
		private String shippingAddress;
		private String shippingZIP;
		private String shippingCity;
		private String shippingState;
		private String shippingCountry;
		private String shippingPhone;
		private String HTTPSessionId;
		private String terminalCode;
		private String userCommerce;
		private String userCodePayme;
		private String descriptionProducts;
		private String programmingLanguage;
		private String purchaseVerification;
		private String mcc;
		private String commerceAssociated;

		// Parametros de respuesta
		private String authorizationResult;
		private String authorizationCode;
		private String errorCode;
		private String errorMessage;
		private String bin;
		private String brand;
		private String paymentReferenceCode;

		// Variable donde se almacenará el purchaseVerification creado por el Comercio
		private String purchaseVerificationComercio;
		
		//Otros medio de pago
		private String payBrandRedirect;
		private String selectedPayType;
		private String selectedPayBrand;
		private String selectedPayBrandLevel1;

		// Reservados
		private String reserved1;
		private String reserved2;
		private String reserved3;
		private String reserved4;
		private String reserved5;
		private String reserved6;
		private String reserved7;
		private String reserved8;
		private String reserved9;
		private String reserved10;
		private String reserved11;
		private String reserved12;
		private String reserved13;
		private String reserved14;
		private String reserved15;
		private String reserved16;
		private String reserved17;
		private String reserved18;
		private String reserved19;
		private String reserved20;
		private String reserved21;
		private String reserved22;
		private String reserved23;
		private String reserved24;
		private String reserved25;
		private String reserved26;
		private String reserved27;
		private String reserved28;
		private String reserved29;
		private String reserved30;
		private String reserved31;
		private String reserved32;
		private String reserved33;
		private String reserved34;
		private String reserved35;
		private String reserved36;
		private String reserved37;
		private String reserved38;
		private String reserved39;
		private String reserved40;
		
		//pagos alternativos
		private String numeroCip;

		public VPOS2() {
			super();
		}
		
		

		public VPOS2(String acquirerId, String idCommerce, String purchaseOperationNumber, String purchaseAmount,
				String purchaseCurrencyCode, String language, String shippingFirstName, String shippingLastName,
				String shippingEmail, String shippingAddress, String shippingZIP, String shippingCity,
				String shippingState, String shippingCountry, String userCommerce, String userCodePayme,
				String descriptionProducts, String programmingLanguage) {
			super();
			this.acquirerId = acquirerId;
			this.idCommerce = idCommerce;
			this.purchaseOperationNumber = purchaseOperationNumber;
			this.purchaseAmount = purchaseAmount;
			this.purchaseCurrencyCode = purchaseCurrencyCode;
			this.language = language;
			this.shippingFirstName = shippingFirstName;
			this.shippingLastName = shippingLastName;
			this.shippingEmail = shippingEmail;
			this.shippingAddress = shippingAddress;
			this.shippingZIP = shippingZIP;
			this.shippingCity = shippingCity;
			this.shippingState = shippingState;
			this.shippingCountry = shippingCountry;
			this.userCommerce = userCommerce;
			this.userCodePayme = userCodePayme;
			this.descriptionProducts = descriptionProducts;
			this.programmingLanguage = programmingLanguage;
		}



		public VPOS2(String acquirerId, String idCommerce, String purchaseOperationNumber, String purchaseAmount,
				String purchaseCurrencyCode, String shippingFirstName, String shippingLastName, String shippingEmail,
				String shippingAddress, String shippingZIP, String shippingCity, String shippingState,
				String shippingCountry, String descriptionProducts, String authorizationResult,
				String authorizationCode, String errorCode, String errorMessage, String bin, String brand,
				String paymentReferenceCode, String purchaseVerificationComercio, String reserved1, String reserved2,
				String reserved3, String reserved4, String reserved5, String reserved6, String reserved7,
				String reserved8, String reserved9, String reserved10, String reserved11, String reserved12,
				String reserved13, String reserved14, String reserved15, String reserved16, String reserved17,
				String reserved18, String reserved19, String reserved20, String reserved21, String reserved22,
				String reserved23, String reserved24, String reserved25, String reserved26, String reserved27,
				String reserved28, String reserved29, String reserved30, String reserved31, String reserved32,
				String reserved33, String reserved34, String reserved35, String reserved36, String reserved37,
				String reserved38, String reserved39, String reserved40, String numeroCip) {
			super();
			this.acquirerId = acquirerId;
			this.idCommerce = idCommerce;
			this.purchaseOperationNumber = purchaseOperationNumber;
			this.purchaseAmount = purchaseAmount;
			this.purchaseCurrencyCode = purchaseCurrencyCode;
			this.shippingFirstName = shippingFirstName;
			this.shippingLastName = shippingLastName;
			this.shippingEmail = shippingEmail;
			this.shippingAddress = shippingAddress;
			this.shippingZIP = shippingZIP;
			this.shippingCity = shippingCity;
			this.shippingState = shippingState;
			this.shippingCountry = shippingCountry;
			this.descriptionProducts = descriptionProducts;
			this.authorizationResult = authorizationResult;
			this.authorizationCode = authorizationCode;
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
			this.bin = bin;
			this.brand = brand;
			this.paymentReferenceCode = paymentReferenceCode;
			this.purchaseVerificationComercio = purchaseVerificationComercio;
			this.reserved1 = reserved1;
			this.reserved2 = reserved2;
			this.reserved3 = reserved3;
			this.reserved4 = reserved4;
			this.reserved5 = reserved5;
			this.reserved6 = reserved6;
			this.reserved7 = reserved7;
			this.reserved8 = reserved8;
			this.reserved9 = reserved9;
			this.reserved10 = reserved10;
			this.reserved11 = reserved11;
			this.reserved12 = reserved12;
			this.reserved13 = reserved13;
			this.reserved14 = reserved14;
			this.reserved15 = reserved15;
			this.reserved16 = reserved16;
			this.reserved17 = reserved17;
			this.reserved18 = reserved18;
			this.reserved19 = reserved19;
			this.reserved20 = reserved20;
			this.reserved21 = reserved21;
			this.reserved22 = reserved22;
			this.reserved23 = reserved23;
			this.reserved24 = reserved24;
			this.reserved25 = reserved25;
			this.reserved26 = reserved26;
			this.reserved27 = reserved27;
			this.reserved28 = reserved28;
			this.reserved29 = reserved29;
			this.reserved30 = reserved30;
			this.reserved31 = reserved31;
			this.reserved32 = reserved32;
			this.reserved33 = reserved33;
			this.reserved34 = reserved34;
			this.reserved35 = reserved35;
			this.reserved36 = reserved36;
			this.reserved37 = reserved37;
			this.reserved38 = reserved38;
			this.reserved39 = reserved39;
			this.reserved40 = reserved40;
			this.numeroCip = numeroCip;
		}



		public VPOS2(String acquirerId, String idCommerce, String purchaseOperationNumber, String purchaseAmount,
				String purchaseCurrencyCode, String commerceMallId, String language, String billingFirstName,
				String billingLastName, String billingEmail, String billingAddress, String billingZIP,
				String billingCity, String billingState, String billingCountry, String billingPhone,
				String shippingFirstName, String shippingLastName, String shippingEmail, String shippingAddress,
				String shippingZIP, String shippingCity, String shippingState, String shippingCountry,
				String shippingPhone, String hTTPSessionId, String terminalCode, String userCommerce,
				String userCodePayme, String descriptionProducts, String programmingLanguage,
				String purchaseVerification, String mcc, String commerceAssociated, String authorizationResult,
				String authorizationCode, String errorCode, String errorMessage, String bin, String brand,
				String paymentReferenceCode, String purchaseVerificationComercio, String payBrandRedirect,
				String selectedPayType, String selectedPayBrand, String selectedPayBrandLevel1, String reserved1,
				String reserved2, String reserved3, String reserved4, String reserved5, String reserved6,
				String reserved7, String reserved8, String reserved9, String reserved10, String reserved11,
				String reserved12, String reserved13, String reserved14, String reserved15, String reserved16,
				String reserved17, String reserved18, String reserved19, String reserved20, String reserved21,
				String reserved22, String reserved23, String reserved24, String reserved25, String reserved26,
				String reserved27, String reserved28, String reserved29, String reserved30, String reserved31,
				String reserved32, String reserved33, String reserved34, String reserved35, String reserved36,
				String reserved37, String reserved38, String reserved39, String reserved40, String numeroCip) {
			super();
			this.acquirerId = acquirerId;
			this.idCommerce = idCommerce;
			this.purchaseOperationNumber = purchaseOperationNumber;
			this.purchaseAmount = purchaseAmount;
			this.purchaseCurrencyCode = purchaseCurrencyCode;
			this.commerceMallId = commerceMallId;
			this.language = language;
			this.billingFirstName = billingFirstName;
			this.billingLastName = billingLastName;
			this.billingEmail = billingEmail;
			this.billingAddress = billingAddress;
			this.billingZIP = billingZIP;
			this.billingCity = billingCity;
			this.billingState = billingState;
			this.billingCountry = billingCountry;
			this.billingPhone = billingPhone;
			this.shippingFirstName = shippingFirstName;
			this.shippingLastName = shippingLastName;
			this.shippingEmail = shippingEmail;
			this.shippingAddress = shippingAddress;
			this.shippingZIP = shippingZIP;
			this.shippingCity = shippingCity;
			this.shippingState = shippingState;
			this.shippingCountry = shippingCountry;
			this.shippingPhone = shippingPhone;
			HTTPSessionId = hTTPSessionId;
			this.terminalCode = terminalCode;
			this.userCommerce = userCommerce;
			this.userCodePayme = userCodePayme;
			this.descriptionProducts = descriptionProducts;
			this.programmingLanguage = programmingLanguage;
			this.purchaseVerification = purchaseVerification;
			this.mcc = mcc;
			this.commerceAssociated = commerceAssociated;
			this.authorizationResult = authorizationResult;
			this.authorizationCode = authorizationCode;
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
			this.bin = bin;
			this.brand = brand;
			this.paymentReferenceCode = paymentReferenceCode;
			this.purchaseVerificationComercio = purchaseVerificationComercio;
			this.payBrandRedirect = payBrandRedirect;
			this.selectedPayType = selectedPayType;
			this.selectedPayBrand = selectedPayBrand;
			this.selectedPayBrandLevel1 = selectedPayBrandLevel1;
			this.reserved1 = reserved1;
			this.reserved2 = reserved2;
			this.reserved3 = reserved3;
			this.reserved4 = reserved4;
			this.reserved5 = reserved5;
			this.reserved6 = reserved6;
			this.reserved7 = reserved7;
			this.reserved8 = reserved8;
			this.reserved9 = reserved9;
			this.reserved10 = reserved10;
			this.reserved11 = reserved11;
			this.reserved12 = reserved12;
			this.reserved13 = reserved13;
			this.reserved14 = reserved14;
			this.reserved15 = reserved15;
			this.reserved16 = reserved16;
			this.reserved17 = reserved17;
			this.reserved18 = reserved18;
			this.reserved19 = reserved19;
			this.reserved20 = reserved20;
			this.reserved21 = reserved21;
			this.reserved22 = reserved22;
			this.reserved23 = reserved23;
			this.reserved24 = reserved24;
			this.reserved25 = reserved25;
			this.reserved26 = reserved26;
			this.reserved27 = reserved27;
			this.reserved28 = reserved28;
			this.reserved29 = reserved29;
			this.reserved30 = reserved30;
			this.reserved31 = reserved31;
			this.reserved32 = reserved32;
			this.reserved33 = reserved33;
			this.reserved34 = reserved34;
			this.reserved35 = reserved35;
			this.reserved36 = reserved36;
			this.reserved37 = reserved37;
			this.reserved38 = reserved38;
			this.reserved39 = reserved39;
			this.reserved40 = reserved40;
			this.numeroCip = numeroCip;
		}

		public String getAcquirerId() {
			return acquirerId;
		}

		public void setAcquirerId(String acquirerId) {
			this.acquirerId = acquirerId;
		}

		public String getIdCommerce() {
			return idCommerce;
		}

		public void setIdCommerce(String idCommerce) {
			this.idCommerce = idCommerce;
		}

		public String getPurchaseOperationNumber() {
			return purchaseOperationNumber;
		}

		public void setPurchaseOperationNumber(String purchaseOperationNumber) {
			this.purchaseOperationNumber = purchaseOperationNumber;
		}

		public String getPurchaseAmount() {
			return purchaseAmount;
		}

		public void setPurchaseAmount(String purchaseAmount) {
			this.purchaseAmount = purchaseAmount;
		}

		public String getPurchaseCurrencyCode() {
			return purchaseCurrencyCode;
		}

		public void setPurchaseCurrencyCode(String purchaseCurrencyCode) {
			this.purchaseCurrencyCode = purchaseCurrencyCode;
		}

		public String getCommerceMallId() {
			return commerceMallId;
		}

		public void setCommerceMallId(String commerceMallId) {
			this.commerceMallId = commerceMallId;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getBillingFirstName() {
			return billingFirstName;
		}

		public void setBillingFirstName(String billingFirstName) {
			this.billingFirstName = billingFirstName;
		}

		public String getBillingLastName() {
			return billingLastName;
		}

		public void setBillingLastName(String billingLastName) {
			this.billingLastName = billingLastName;
		}

		public String getBillingEmail() {
			return billingEmail;
		}

		public void setBillingEmail(String billingEmail) {
			this.billingEmail = billingEmail;
		}

		public String getBillingAddress() {
			return billingAddress;
		}

		public void setBillingAddress(String billingAddress) {
			this.billingAddress = billingAddress;
		}

		public String getBillingZIP() {
			return billingZIP;
		}

		public void setBillingZIP(String billingZIP) {
			this.billingZIP = billingZIP;
		}

		public String getBillingCity() {
			return billingCity;
		}

		public void setBillingCity(String billingCity) {
			this.billingCity = billingCity;
		}

		public String getBillingState() {
			return billingState;
		}

		public void setBillingState(String billingState) {
			this.billingState = billingState;
		}

		public String getBillingCountry() {
			return billingCountry;
		}

		public void setBillingCountry(String billingCountry) {
			this.billingCountry = billingCountry;
		}

		public String getBillingPhone() {
			return billingPhone;
		}

		public void setBillingPhone(String billingPhone) {
			this.billingPhone = billingPhone;
		}

		public String getShippingFirstName() {
			return shippingFirstName;
		}

		public void setShippingFirstName(String shippingFirstName) {
			this.shippingFirstName = shippingFirstName;
		}

		public String getShippingLastName() {
			return shippingLastName;
		}

		public void setShippingLastName(String shippingLastName) {
			this.shippingLastName = shippingLastName;
		}

		public String getShippingEmail() {
			return shippingEmail;
		}

		public void setShippingEmail(String shippingEmail) {
			this.shippingEmail = shippingEmail;
		}

		public String getShippingAddress() {
			return shippingAddress;
		}

		public void setShippingAddress(String shippingAddress) {
			this.shippingAddress = shippingAddress;
		}

		public String getShippingZIP() {
			return shippingZIP;
		}

		public void setShippingZIP(String shippingZIP) {
			this.shippingZIP = shippingZIP;
		}

		public String getShippingCity() {
			return shippingCity;
		}

		public void setShippingCity(String shippingCity) {
			this.shippingCity = shippingCity;
		}

		public String getShippingState() {
			return shippingState;
		}

		public void setShippingState(String shippingState) {
			this.shippingState = shippingState;
		}

		public String getShippingCountry() {
			return shippingCountry;
		}

		public void setShippingCountry(String shippingCountry) {
			this.shippingCountry = shippingCountry;
		}

		public String getShippingPhone() {
			return shippingPhone;
		}

		public void setShippingPhone(String shippingPhone) {
			this.shippingPhone = shippingPhone;
		}

		public String getHTTPSessionId() {
			return HTTPSessionId;
		}

		public void setHTTPSessionId(String hTTPSessionId) {
			HTTPSessionId = hTTPSessionId;
		}

		public String getTerminalCode() {
			return terminalCode;
		}

		public void setTerminalCode(String terminalCode) {
			this.terminalCode = terminalCode;
		}

		public String getUserCommerce() {
			return userCommerce;
		}

		public void setUserCommerce(String userCommerce) {
			this.userCommerce = userCommerce;
		}

		public String getUserCodePayme() {
			return userCodePayme;
		}

		public void setUserCodePayme(String userCodePayme) {
			this.userCodePayme = userCodePayme;
		}

		public String getDescriptionProducts() {
			return descriptionProducts;
		}

		public void setDescriptionProducts(String descriptionProducts) {
			this.descriptionProducts = descriptionProducts;
		}

		public String getProgrammingLanguage() {
			return programmingLanguage;
		}

		public void setProgrammingLanguage(String programmingLanguage) {
			this.programmingLanguage = programmingLanguage;
		}

		public String getPurchaseVerification() {
			return purchaseVerification;
		}

		public void setPurchaseVerification(String purchaseVerification) {
			this.purchaseVerification = purchaseVerification;
		}

		public String getMcc() {
			return mcc;
		}

		public void setMcc(String mcc) {
			this.mcc = mcc;
		}

		public String getCommerceAssociated() {
			return commerceAssociated;
		}

		public void setCommerceAssociated(String commerceAssociated) {
			this.commerceAssociated = commerceAssociated;
		}

		public String getAuthorizationResult() {
			return authorizationResult;
		}

		public void setAuthorizationResult(String authorizationResult) {
			this.authorizationResult = authorizationResult;
		}

		public String getAuthorizationCode() {
			return authorizationCode;
		}

		public void setAuthorizationCode(String authorizationCode) {
			this.authorizationCode = authorizationCode;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getBin() {
			return bin;
		}

		public void setBin(String bin) {
			this.bin = bin;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getPaymentReferenceCode() {
			return paymentReferenceCode;
		}

		public void setPaymentReferenceCode(String paymentReferenceCode) {
			this.paymentReferenceCode = paymentReferenceCode;
		}

		public String getPurchaseVerificationComercio() {
			return purchaseVerificationComercio;
		}

		public void setPurchaseVerificationComercio(String purchaseVerificationComercio) {
			this.purchaseVerificationComercio = purchaseVerificationComercio;
		}

		public String getPayBrandRedirect() {
			return payBrandRedirect;
		}

		public void setPayBrandRedirect(String payBrandRedirect) {
			this.payBrandRedirect = payBrandRedirect;
		}

		public String getSelectedPayType() {
			return selectedPayType;
		}

		public void setSelectedPayType(String selectedPayType) {
			this.selectedPayType = selectedPayType;
		}

		public String getSelectedPayBrand() {
			return selectedPayBrand;
		}

		public void setSelectedPayBrand(String selectedPayBrand) {
			this.selectedPayBrand = selectedPayBrand;
		}

		public String getSelectedPayBrandLevel1() {
			return selectedPayBrandLevel1;
		}

		public void setSelectedPayBrandLevel1(String selectedPayBrandLevel1) {
			this.selectedPayBrandLevel1 = selectedPayBrandLevel1;
		}

		public String getReserved1() {
			return reserved1;
		}

		public void setReserved1(String reserved1) {
			this.reserved1 = reserved1;
		}

		public String getReserved2() {
			return reserved2;
		}

		public void setReserved2(String reserved2) {
			this.reserved2 = reserved2;
		}

		public String getReserved3() {
			return reserved3;
		}

		public void setReserved3(String reserved3) {
			this.reserved3 = reserved3;
		}

		public String getReserved4() {
			return reserved4;
		}

		public void setReserved4(String reserved4) {
			this.reserved4 = reserved4;
		}

		public String getReserved5() {
			return reserved5;
		}

		public void setReserved5(String reserved5) {
			this.reserved5 = reserved5;
		}

		public String getReserved6() {
			return reserved6;
		}

		public void setReserved6(String reserved6) {
			this.reserved6 = reserved6;
		}

		public String getReserved7() {
			return reserved7;
		}

		public void setReserved7(String reserved7) {
			this.reserved7 = reserved7;
		}

		public String getReserved8() {
			return reserved8;
		}

		public void setReserved8(String reserved8) {
			this.reserved8 = reserved8;
		}

		public String getReserved9() {
			return reserved9;
		}

		public void setReserved9(String reserved9) {
			this.reserved9 = reserved9;
		}

		public String getReserved10() {
			return reserved10;
		}

		public void setReserved10(String reserved10) {
			this.reserved10 = reserved10;
		}

		public String getReserved11() {
			return reserved11;
		}

		public void setReserved11(String reserved11) {
			this.reserved11 = reserved11;
		}

		public String getReserved12() {
			return reserved12;
		}

		public void setReserved12(String reserved12) {
			this.reserved12 = reserved12;
		}

		public String getReserved13() {
			return reserved13;
		}

		public void setReserved13(String reserved13) {
			this.reserved13 = reserved13;
		}

		public String getReserved14() {
			return reserved14;
		}

		public void setReserved14(String reserved14) {
			this.reserved14 = reserved14;
		}

		public String getReserved15() {
			return reserved15;
		}

		public void setReserved15(String reserved15) {
			this.reserved15 = reserved15;
		}

		public String getReserved16() {
			return reserved16;
		}

		public void setReserved16(String reserved16) {
			this.reserved16 = reserved16;
		}

		public String getReserved17() {
			return reserved17;
		}

		public void setReserved17(String reserved17) {
			this.reserved17 = reserved17;
		}

		public String getReserved18() {
			return reserved18;
		}

		public void setReserved18(String reserved18) {
			this.reserved18 = reserved18;
		}

		public String getReserved19() {
			return reserved19;
		}

		public void setReserved19(String reserved19) {
			this.reserved19 = reserved19;
		}

		public String getReserved20() {
			return reserved20;
		}

		public void setReserved20(String reserved20) {
			this.reserved20 = reserved20;
		}

		public String getReserved21() {
			return reserved21;
		}

		public void setReserved21(String reserved21) {
			this.reserved21 = reserved21;
		}

		public String getReserved22() {
			return reserved22;
		}

		public void setReserved22(String reserved22) {
			this.reserved22 = reserved22;
		}

		public String getReserved23() {
			return reserved23;
		}

		public void setReserved23(String reserved23) {
			this.reserved23 = reserved23;
		}

		public String getReserved24() {
			return reserved24;
		}

		public void setReserved24(String reserved24) {
			this.reserved24 = reserved24;
		}

		public String getReserved25() {
			return reserved25;
		}

		public void setReserved25(String reserved25) {
			this.reserved25 = reserved25;
		}

		public String getReserved26() {
			return reserved26;
		}

		public void setReserved26(String reserved26) {
			this.reserved26 = reserved26;
		}

		public String getReserved27() {
			return reserved27;
		}

		public void setReserved27(String reserved27) {
			this.reserved27 = reserved27;
		}

		public String getReserved28() {
			return reserved28;
		}

		public void setReserved28(String reserved28) {
			this.reserved28 = reserved28;
		}

		public String getReserved29() {
			return reserved29;
		}

		public void setReserved29(String reserved29) {
			this.reserved29 = reserved29;
		}

		public String getReserved30() {
			return reserved30;
		}

		public void setReserved30(String reserved30) {
			this.reserved30 = reserved30;
		}

		public String getReserved31() {
			return reserved31;
		}

		public void setReserved31(String reserved31) {
			this.reserved31 = reserved31;
		}

		public String getReserved32() {
			return reserved32;
		}

		public void setReserved32(String reserved32) {
			this.reserved32 = reserved32;
		}

		public String getReserved33() {
			return reserved33;
		}

		public void setReserved33(String reserved33) {
			this.reserved33 = reserved33;
		}

		public String getReserved34() {
			return reserved34;
		}

		public void setReserved34(String reserved34) {
			this.reserved34 = reserved34;
		}

		public String getReserved35() {
			return reserved35;
		}

		public void setReserved35(String reserved35) {
			this.reserved35 = reserved35;
		}

		public String getReserved36() {
			return reserved36;
		}

		public void setReserved36(String reserved36) {
			this.reserved36 = reserved36;
		}

		public String getReserved37() {
			return reserved37;
		}

		public void setReserved37(String reserved37) {
			this.reserved37 = reserved37;
		}

		public String getReserved38() {
			return reserved38;
		}

		public void setReserved38(String reserved38) {
			this.reserved38 = reserved38;
		}

		public String getReserved39() {
			return reserved39;
		}

		public void setReserved39(String reserved39) {
			this.reserved39 = reserved39;
		}

		public String getReserved40() {
			return reserved40;
		}

		public void setReserved40(String reserved40) {
			this.reserved40 = reserved40;
		}

		public String getNumeroCip() {
			return numeroCip;
		}

		public void setNumeroCip(String numeroCip) {
			this.numeroCip = numeroCip;
		}



		@Override
		public String toString() {
			return "VPOS2 [acquirerId=" + acquirerId + ", idCommerce=" + idCommerce + ", purchaseOperationNumber="
					+ purchaseOperationNumber + ", purchaseAmount=" + purchaseAmount + ", purchaseCurrencyCode="
					+ purchaseCurrencyCode + ", commerceMallId=" + commerceMallId + ", language=" + language
					+ ", billingFirstName=" + billingFirstName + ", billingLastName=" + billingLastName
					+ ", billingEmail=" + billingEmail + ", billingAddress=" + billingAddress + ", billingZIP="
					+ billingZIP + ", billingCity=" + billingCity + ", billingState=" + billingState
					+ ", billingCountry=" + billingCountry + ", billingPhone=" + billingPhone + ", shippingFirstName="
					+ shippingFirstName + ", shippingLastName=" + shippingLastName + ", shippingEmail=" + shippingEmail
					+ ", shippingAddress=" + shippingAddress + ", shippingZIP=" + shippingZIP + ", shippingCity="
					+ shippingCity + ", shippingState=" + shippingState + ", shippingCountry=" + shippingCountry
					+ ", shippingPhone=" + shippingPhone + ", HTTPSessionId=" + HTTPSessionId + ", terminalCode="
					+ terminalCode + ", userCommerce=" + userCommerce + ", userCodePayme=" + userCodePayme
					+ ", descriptionProducts=" + descriptionProducts + ", programmingLanguage=" + programmingLanguage
					+ ", purchaseVerification=" + purchaseVerification + ", mcc=" + mcc + ", commerceAssociated="
					+ commerceAssociated + ", authorizationResult=" + authorizationResult + ", authorizationCode="
					+ authorizationCode + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", bin=" + bin
					+ ", brand=" + brand + ", paymentReferenceCode=" + paymentReferenceCode
					+ ", purchaseVerificationComercio=" + purchaseVerificationComercio + ", payBrandRedirect="
					+ payBrandRedirect + ", selectedPayType=" + selectedPayType + ", selectedPayBrand="
					+ selectedPayBrand + ", selectedPayBrandLevel1=" + selectedPayBrandLevel1 + ", reserved1="
					+ reserved1 + ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + ", reserved4=" + reserved4
					+ ", reserved5=" + reserved5 + ", reserved6=" + reserved6 + ", reserved7=" + reserved7
					+ ", reserved8=" + reserved8 + ", reserved9=" + reserved9 + ", reserved10=" + reserved10
					+ ", reserved11=" + reserved11 + ", reserved12=" + reserved12 + ", reserved13=" + reserved13
					+ ", reserved14=" + reserved14 + ", reserved15=" + reserved15 + ", reserved16=" + reserved16
					+ ", reserved17=" + reserved17 + ", reserved18=" + reserved18 + ", reserved19=" + reserved19
					+ ", reserved20=" + reserved20 + ", reserved21=" + reserved21 + ", reserved22=" + reserved22
					+ ", reserved23=" + reserved23 + ", reserved24=" + reserved24 + ", reserved25=" + reserved25
					+ ", reserved26=" + reserved26 + ", reserved27=" + reserved27 + ", reserved28=" + reserved28
					+ ", reserved29=" + reserved29 + ", reserved30=" + reserved30 + ", reserved31=" + reserved31
					+ ", reserved32=" + reserved32 + ", reserved33=" + reserved33 + ", reserved34=" + reserved34
					+ ", reserved35=" + reserved35 + ", reserved36=" + reserved36 + ", reserved37=" + reserved37
					+ ", reserved38=" + reserved38 + ", reserved39=" + reserved39 + ", reserved40=" + reserved40
					+ ", numeroCip=" + numeroCip + "]";
		}
		
		
		
		
	
}
