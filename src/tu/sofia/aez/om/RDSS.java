package tu.sofia.aez.om;

public class RDSS implements RejimNaSpirane {

	@Override
	public String getName() {
		return "Динамично спиране със самовъзбуждане";
	}
	@Override
	public RejimEnum getType() {
		return RejimEnum.RDSS;
	}
}
