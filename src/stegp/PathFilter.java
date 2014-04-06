package stegp;

public class PathFilter {

    public static String extension(String path) {
        int dot = path.lastIndexOf('.');
        return path.substring(dot + 1);
    }

    public static String filename(String path) {
        int dot = path.lastIndexOf('.');
        int sep = path.lastIndexOf('\\');
        System.out.println(dot + " " + sep);
        return path.substring(sep + 1, dot);
    }

    public static String path(String path) {
        int sep = path.lastIndexOf('\\');
        return path.substring(0, sep) + '\\';
    }
}
