public class ObjectCounter {
    private static int objectCount = 0;

    public static int getObjectCount() {
        return objectCount;
    }

    public static int incrementObjectCount() {
        return ++objectCount;
    }
}
