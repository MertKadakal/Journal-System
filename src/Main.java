import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //load the input files - create author objects - set the output path
        String output = "src\\output.txt";
        FileOutput.writeToFile(output, "", false, false);
        String[] articles_txt = FileInput.readFile("src\\IO_1\\article1.txt", false, true);
        String[] authors_file_txt = FileInput.readFile("src\\IO_1\\author.txt", false, true);
        String[] commands_txt = FileInput.readFile("src\\IO_1\\command.txt", false, true);

        ArrayList<Author> authors_list = new ArrayList<>();
        for (String line1 : authors_file_txt) {
            String[] line = line1.split(" ");
            authors_list.add(new Author(line[1], line[2], line[3], line[4], line[5]));
        }

        //add articles as a list to author objects
        for (int i = 0; i < authors_file_txt.length; i++) {
            ArrayList<String> articles = new ArrayList<>();
            if (!(authors_file_txt[i].split(" ").length == 6)) {
                int j = 6;
                while (!(j > authors_file_txt[i].split(" ").length-1)) {
                    articles.add(authors_file_txt[i].split(" ")[j]);
                    j++;
                }
            }
            authors_list.get(i).articles = articles;
        }

        //create a dictionary for articles
        HashMap<String, String> articles_map = new HashMap<>();
        for (String line : articles_txt) {
            articles_map.put(line.split(" ")[1], line.split(" ")[2]);
        }

        //execute commands: list, completeAll, sortedAll, del sth
        for (String command : commands_txt) {
            switch (command.split(" ")[0]) {
                case "list":
                    FileOutput.writeToFile(output, "----------------------------------------------List---------------------------------------------", true, true);
                    for (Author author : authors_list) {
                        FileOutput.writeToFile(output, String.format("Author:%s  %s  %s %s  %s", author.id, author.name, author.university, author.department, author.email) , true, true);
                        for (String article : author.articles) {
                            FileOutput.writeToFile(output, String.format("+%s    %s", article, articles_map.get(article)) , true, true);
                        }
                        if (authors_list.indexOf(author) == authors_list.size()-1) {
                            FileOutput.writeToFile(output, "" , true, false);
                        }
                        else {
                            FileOutput.writeToFile(output, "" , true, true);
                        }
                    }
                    FileOutput.writeToFile(output, "----------------------------------------------End---------------------------------------------\n" , true, true);
                    break;

                case "completeAll":
                    for (Author author : authors_list) {
                        for (String article : articles_txt) {
                            if ((article.split(" ")[1].substring(0,3).equals(author.id)) && !(author.articles.contains(article.split(" ")[1])) && (author.articles.size() < 5)) {
                                author.articles.add(article.split(" ")[1]);
                            }
                        }
                    }
                    FileOutput.writeToFile(output, "*************************************CompleteAll Successful*************************************\n" , true, true);
                    break;
            }
        }
    }
}