
public class SameClassSubclassRule implements GroupRule {
	public boolean inSameGroup(Resource r1, Resource r2) {
		return r1 instanceof r2.getClass() || r2 instanceof r1.getClass();
	}
}