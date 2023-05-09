public class Main {
	public static void main(String[] args) {
		playerDemo();
		playerRecordDemo();
	}

	public static void playerDemo() {
		Player player = new Player();
		player.setName("lavantien");
		player.setEmail("test@test.com");
		player.setPassword("jkjkjkjk");
		player.setElo(600);
		player.printStates();
	}

	public static void playerRecordDemo() {
		PlayerRecord playerRecord = new PlayerRecord("lavantien", "test@test.com", "jkjkjkjk", 600);
		System.out.println(playerRecord.name() + ", " + playerRecord.email() + ", " + playerRecord.password() + ", "
				+ playerRecord.elo());
	}
}

record PlayerRecord(String name, String email, String password, int elo) {
	public PlayerRecord {
		if (elo < 1000) {
			System.out.println(name + ", you suck!");
		}
	}
}

class Player {
	private String name;
	private String email;
	private String password;
	private int elo;

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}

	public void printStates() {
		System.out.println(this.name + ", " + this.email + ", " + this.password + ", " + this.elo);
	}
}
