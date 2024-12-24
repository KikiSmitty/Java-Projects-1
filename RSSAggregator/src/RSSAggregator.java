import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Kierra Smith
 *
 */
public final class RSSAggregator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSAggregator() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1><a href="">the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <table border="1">
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        //initialize variables
        int titleIndex = getChildElement(channel, "title");
        int linkIndex = getChildElement(channel, "link");
        int descriptionIndex = getChildElement(channel, "description");
        String link = "No Link Available";
        String title = "Empty Title";
        String description = "No description";

        if (channel.child(titleIndex).numberOfChildren() > 0) {
            //child of title if there is a child
            title = channel.child(titleIndex).child(0).label();
        }
        //child of index if there is a child
        if (channel.child(linkIndex).numberOfChildren() > 0) {
            link = channel.child(linkIndex).child(0).label();
        }
        //child of description if there is a child
        if (channel.child(descriptionIndex).numberOfChildren() > 0) {
            description = channel.child(getChildElement(channel, "description"))
                    .child(0).label();
        }

        //print HTML tags
        out.println("<html><head><title>" + title + "</title></head><body>");
        out.println("<h1><a href=" + link + ">" + title + "</a></h1>");
        out.println("<p>" + description + "</p>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>Date</th>" + "<th>Source</th>" + "<th>News</th>"
                + "</tr>");

    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        //print HTML tags
        out.println("</table>");
        out.println("</body></html>");

    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        //initialize child num
        int childNum = -1;
        //iterate through each child in xml document
        for (int i = 0; i < xml.numberOfChildren(); i++) {
            //if child label is equal to tag update childNum to equal i
            if (xml.child(i).label().equals(tag) && xml.child(i).isTag()) {
                childNum = i;
            }
        }
        //return statement
        return childNum;

    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        //initialize int and string variables
        int titleIndex = getChildElement(item, "title");
        int linkIndex = getChildElement(item, "link");
        int descriptionIndex = getChildElement(item, "description");
        int dateIndex = getChildElement(item, "pubDate");
        int sourceIndex = getChildElement(item, "source");
        String link = "No Link Available";
        String title = "No title available";
        String date = "No date available";
        String source = "No source available";
        String sourceLabel = "No source available";

        //check if title element has children
        if (titleIndex >= 0) {
            //if it does exist check if the number of children is not -1
            if (item.child(titleIndex).numberOfChildren() != 0) {
                //if title has children then assign title to the label of its child
                title = item.child(titleIndex).child(0).label();

            }
        }

        //check if description element has children
        if (descriptionIndex >= 0) {
            //if it does exist check if the number of children is not -1
            if (item.child(descriptionIndex).numberOfChildren() != 0
                    && item.child(titleIndex).numberOfChildren() == 0) {
                //if description has children then assign title to the label of its child
                title = item.child(descriptionIndex).child(0).label();

            }
        }

        //check if link element has children
        if (linkIndex >= 0) {
            // link = item.child(linkIndex).child(0).label();
            if (item.child(linkIndex).numberOfChildren() != 0) {
                //if date has children then assign date to the label of its child
                link = item.child(linkIndex).child(0).label();
            }
        }

        //check if date element has children
        if (dateIndex >= 0) {
            //if it does exist check if the number of children is not -1
            if (item.child(dateIndex).numberOfChildren() != 0) {
                //if date has children then assign date to the label of its child
                date = item.child(dateIndex).child(0).label();
            }
        }
        //check if source element has children
        if (sourceIndex >= 0) {
            //if it does exist check if the number of children is not -1
            if (item.child(sourceIndex).numberOfChildren() != 0) {
                //if source has children then assign sourceLabel to the label of its child
                //and assign source to the attribute value of "url"
                sourceLabel = item.child(getChildElement(item, "source"))
                        .child(0).label();
                source = item.child(sourceIndex).attributeValue("url");

            }
        }

        //print HTML tags
        out.println("<tr>");
        out.println("<td>" + date + "</td>");
        if (source.equals("No source available")) {
            out.println("<td>" + sourceLabel + "</td>");
        } else {
            out.println(
                    "<td><a href=" + source + ">" + sourceLabel + "</a></td>");
        }
        out.println("<td><a href=" + link + ">" + title + "</a></td>");
        out.println("</tr>");
    }

    /**
     * Processes one XML RSS (version 2.0) feed from a given URL converting it
     * into the corresponding HTML output file.
     *
     * @param url
     *            the URL of the RSS feed
     * @param file
     *            the name of the HTML output file
     * @param out
     *            the output stream to report progress or errors
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * [reads RSS feed from url, saves HTML document with table of news items
     *   to file, appends to out.content any needed messages]
     * </pre>
     */
    private static void processFeed(String url, String file, SimpleWriter out) {
        //create an xml tree variable that will take the String from url user input
        XMLTree xml = new XMLTree1(url);
        //create a new SimpleWriter variable called fileOut that takes the String fileName
        SimpleWriter fileOut = new SimpleWriter1L(file);
        if (xml.label().equals("rss") && xml.hasAttribute("version")
                && xml.attributeValue("version").equals("2.0")) {
            //call methods
            outputHeader(xml.child(0), fileOut);
            //for loop to iterate through each child in the xml tree from the given url
            for (int i = 0; i < xml.child(0).numberOfChildren(); i++) {
                //if there is a child label that equals item then call the processItem
                //method
                if (xml.child(0).child(i).label().equals("item")) {
                    processItem(xml.child(0).child(i), fileOut);
                }
            }
            outputFooter(fileOut);
            fileOut.close();
        } else {
            out.println("Please enter a valid RSS feed: ");
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        //initialize variables
        SimpleReader in = new SimpleReader1L();
        SimpleWriter consoleOut = new SimpleWriter1L();
        String url = "";
        String fileName = "";
        //ask for user input
        consoleOut.println("Input a valid feeds url: ");
        url = in.nextLine();
        consoleOut.println("Enter your target html file: ");
        fileName = in.nextLine();
        //new simple writer object for fileName
        SimpleWriter out = new SimpleWriter1L(fileName);
        XMLTree xml = new XMLTree1(url);
        //print the header of the file
        out.println("<html><head><title>" + xml.attributeValue("title")
                + "</title>");
        out.println("</head><body>");
        out.println("<h1>" + xml.attributeValue("title") + "</h1>");
        out.println("<ul>");
        //iterate through each RSS file in the feed
        for (int i = 0; i < xml.numberOfChildren(); i++) {
            out.println("<li><a href = " + xml.child(i).attributeValue("file")
                    + ">" + xml.child(i).attributeValue("name") + "</a></li>");
            processFeed(xml.child(i).attributeValue("url"),
                    xml.child(i).attributeValue("file"), consoleOut);
        }
        out.println("</ul>");
        out.println("</body></html>");

        //close SimpleReader and SimpleWriter
        in.close();
        out.close();

    }

}
