import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileEditor.updateFilesFromFolderByStringList("C:\\reps\\mvno-qa-billing-auto\\src\\test\\java\\ru\\tinkoff", stringList -> {
            List<String> newListRow = new ArrayList<>(stringList);
            int count = 0;
            boolean isFound = false;
            for (int i = 0; i < stringList.size(); i++) {
                String row = stringList.get(i);
                if (row.contains("@AllureId(")) {
                    newListRow.add(i + count + 1, "    @TagNewProject");
                    count++;
                    isFound = true;
                }
            }
            if (isFound) {
                newListRow.add(2, "import ru.core.tags.TagNewProject;");
            }
            if (newListRow.get(newListRow.size() - 1).startsWith("}")) {
                newListRow.add("");
            }
            return newListRow;
        });
    }
}