import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathSorter {
    public static void main(String[] args) {
        List<String> xpaths = new ArrayList<>();
        xpaths.add("/root/field[1]/x[2]/path[2]");
        xpaths.add("/root/field[10]/x[1]/path[1]");
        xpaths.add("/root/field[2]/x[2]/path[1]");
        xpaths.add("/root/field[2]/x[2]/path[10]");
        xpaths.add("/root/field[1]/x[10]/path[1]");

        xpaths.sort(new XPathComparator());

        for (String xpath : xpaths) {
            System.out.println(xpath);
        }
    }

    static class XPathComparator implements Comparator<String> {
        private final Pattern pattern = Pattern.compile("\\[(\\d+)]");

        @Override
        public int compare(String o1, String o2) {
            List<Integer> indices1 = extractIndices(o1);
            List<Integer> indices2 = extractIndices(o2);

            int minLength = Math.min(indices1.size(), indices2.size());
            for (int i = 0; i < minLength; i++) {
                int comparison = Integer.compare(indices1.get(i), indices2.get(i));
                if (comparison != 0) {
                    return comparison;
                }
            }

            return Integer.compare(indices1.size(), indices2.size());
        }

        private List<Integer> extractIndices(String xpath) {
            List<Integer> indices = new ArrayList<>();
            Matcher matcher = pattern.matcher(xpath);
            while (matcher.find()) {
                indices.add(Integer.parseInt(matcher.group(1)));
            }
            return indices;
        }
    }
}