package tu.sofia.aez.om;

public class CondenzatornoSpirane implements RejimNaSpirane {

	@Override
	public String getName() {
		return "Кондензаторно спиране";
	}
	@Override
	public RejimEnum getType() {
		return RejimEnum.COND;
	}
}
