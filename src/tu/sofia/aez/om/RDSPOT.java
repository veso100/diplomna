package tu.sofia.aez.om;

public class RDSPOT implements RejimNaSpirane {

	@Override
	public String getName() {
		return "Режим на спиране с положителна обратна връзка по ток";
	}
	@Override
	public RejimEnum getType() {
		return RejimEnum.RDSPOT;
	}

}
