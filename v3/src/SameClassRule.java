
public class SameClassRule implements GroupRule {
	public boolean inSameGroup(Resource r1, Resource r2) {
		return r1.getClass() == r2.getClass();
	}
}