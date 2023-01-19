package csme.RestTest;

public class Reader {
	public static void main(String[] args) {
		String c="a,bc\n"
				+ "";
		String[] lines = c.split("\n");
		for (String line : lines) {
			//System.out.println(line);
			String[] colsList = line.split(",");
			User e=new User();
			for(int i=0;i<colsList.length;i++) {
				//System.out.println(colsList[i]);
				switch(i) {
				case 0: e.id=colsList[i]; break;
				case 1: e.name=colsList[i]; break;
				}
			}
			System.out.println(e);
		}
	}
}

class User{
	public User() {

	}
	public String id;
	public String name;
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
