
public class SameClassSubclassRule implements GroupRule {
	public boolean inSameGroup(Resource r1, Resource r2) {
		return r1.getClass().isAssignableFrom(r2.getClass()) || r2.getClass().isAssignableFrom(r1.getClass());
	}
}