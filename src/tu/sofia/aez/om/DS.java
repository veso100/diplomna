package tu.sofia.aez.om;

public class DS implements RejimNaSpirane {

	@Override
	public String getName() {
		return "Динамично спиране";
	}

	@Override
	public RejimEnum getType() {
		return RejimEnum.DS;
	}

}
