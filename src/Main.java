import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //load the input files - create author objects - set the output path
        ArrayList<String> articles_txt = new ArrayList<>();
        String output = "src\\output.txt";
        FileOutput.writeToFile(output, "", false, false);
        String[] authors_file_txt = FileInput.readFile("src\\IO_3\\author.txt", false, true);
        String[] commands_txt = FileInput.readFile("src\\IO_3\\command.txt", false, true);

        //create the list that consists of authors and their attributes
        ArrayList<Author> authors_list = new ArrayList<>();
        for (String line1 : authors_file_txt) {
            String[] line = line1.split(" ");
            if (line1.split(" ").length == 2) {
                authors_list.add(new Author(line[1], "", "", "", ""));
            }
            else {
                authors_list.add(new Author(line[1], line[2], line[3], line[4], line[5]));
            }
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

        //execute commands: list, completeAll, sortedAll, del sth
        int line_num = 0;
        for (String command : commands_txt) {
            switch (command.split(" ")[0]) {
                case "read":
                    String[] articles_txt_tem = FileInput.readFile(String.format("src\\IO_3\\%s", command.split(" ")[1]), false, true);
                    for (String line : articles_txt_tem) {
                        articles_map.put(line.split(" ")[1], line.split(" ")[2] + "\t" + line.split(" ")[3] + "\t" + line.split(" ")[4]);
                    }
                    for (String line : articles_txt_tem) {
                        articles_txt.add(line);
                    }
                    break;

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
                    if (line_num == commands_txt.length-1) {
                        FileOutput.writeToFile(output, "----------------------------------------------End---------------------------------------------" , true, false);
                    }
                    else {
                        FileOutput.writeToFile(output, "----------------------------------------------End---------------------------------------------\n" , true, true);
                    }
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

                case "sortedAll":
                    for (Author author : authors_list) {
                        Collections.sort(author.articles);
                    }
                    FileOutput.writeToFile(output, "*************************************SortedAll Successful*************************************\n" , true, true);
                    break;

                case "del":
                    String del_author = command.split(" ")[1];
                    int ind = 0;
                    for (Author author : authors_list) {
                        if (author.id.equals(del_author)) {
                            ind = authors_list.indexOf(author);
                        }
                    }
                    authors_list.remove(ind);
                    FileOutput.writeToFile(output, "*************************************del Successful*************************************\n", true, true);
                    break;
            }
            line_num++;
        }
    }
}