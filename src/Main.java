import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] authors_file = FileInput.readFile("src\\IO_1\\author.txt", false, true);
        ArrayList<Author> authors_list = new ArrayList<>();
        for (String line1 : authors_file) {
            String[] line = line1.split(" ");
            authors_list.add(new Author(line[1], line[2], line[3], line[4], line[5]));
        }

        //add articles as a list to author objects
        for (int i = 0; i < authors_file.length; i++) {
            ArrayList<String> articles = new ArrayList<>();
            if (!(authors_file[i].split(" ").length == 6)) {
                int j = 6;
                while (!(j > authors_file[i].split(" ").length-1)) {
                    articles.add(authors_file[i].split(" ")[j]);
                    j++;
                }
            }
            authors_list.get(i).articles = articles;
        }

        for (Author author : authors_list) {
            System.out.println(author.articles);
        }
    }
}