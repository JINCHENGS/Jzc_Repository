package regexp;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则表达式（简单应用）
 *
 * @author cheng.jin
 * @since 2018年04月07日
 */
public class RegExp {
    //java中正则表达式通常有三个类：1.String;2.Pattern;3.Matcher;
    public static void main(String[] args) {
        //简单认识正则表达式的概念(具体正则表达式见API文档)
        //首先介绍String类(String类里有下面这几种方法涉及正则表达式)
        System.out.println("Hello world!".matches("............"));//<.>代表任意字符
        p("a123a".replaceAll("\\d","-"));//<\\d>代表一个数字，正则匹配的所有位被替换
        p("a123a".replaceFirst("\\d","!"));//正则所匹配的首位被替换
        p("aaa".matches("[a-z]{3}"));//<a-z>数量是3
        //Pattern类(下面这种分为三步进行的，效率高于String类)
        //指定为字符串的正则表达式必须首先被编译为此类的实例。
        //然后，可将得到的模式用于创建 Matcher 对象，依照正则表达式，该对象可以与任意字符序列匹配。
        //执行匹配所涉及的所有状态都驻留在匹配器中，所以多个匹配器可以共享同一模式。
        Pattern pattern = compile("[a-z]{3}");//进行预编译
        Matcher matcher = pattern.matcher("aaa");
        p(matcher.matches());
        //初步认识：* ,+ ,?
        p("aaa".matches("a*"));
        p("aaa".matches("a+"));
        p("aaa".matches("a?"));
        p("".matches("a?"));
        p("".matches("a+"));
        p("".matches("a*"));
        p("192.168.127.aaa".matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}"));//<{}>代表的是数量
        p("159".matches("[0-1][0-9][0-9]"));//<[]>代表的是范围

        //正则表达式里的范围(一个中括号只能对应一个字符)
        p("a".matches("[abc]"));//代表是否是abc中的一个
        p("c".matches("[^abc]"));//<^>非运算
        p("a".matches("[a-z]"));//代表是否是a-z中的一个
        p("a".matches("[a-zA-Z]"));//是否是a-z或者A-Z中的一个
        p("a".matches("[a-z]|[A-Z]"));//或者关系
        p("a".matches("[a-z[A-Z]]"));//或者关系
        p("r".matches("[a-z$$[rst]]"));//取交集

        //正则表达式里的另外几个特殊字符 \s ,\w ,\d ,\(正则表达式里一个字符只对应一位，如果匹配的有多位需要加上数量)
        p(" \n\t\r".matches("\\s{1,4}"));//匹配所有空格
        p(" ".matches("\\S"));//<\S>大写的S匹配所有的非空格字符，与小写的相反
        p("a_0".matches("\\w{3}"));//<\w>代表一个单词字符由[a-z][A-Z]_[0-9]组成
        p("abc888^$%".matches("[a-z]{1,3}\\d+[@#$%&^]+"));
        p("\\".matches("\\\\"));

        //boundary , ^ ,$ , \\b
        p("Hello sir".matches("^H.*"));//<^>正则表达式中这个小箭头在中括号里代表相反的意思，在首位则代表匹配的的首字符是^后面的一位
        p("Hello sir".matches(".*ir$"));//<$>表示以什么结尾，这里就是以ir结尾；^和$一个代表以什么开始，一个代表以什么结尾
        p("Hello sir".matches("^H[a-z]+\\b.*"));//<\\b>即boundary边界符，即Hello的边界是一个空格
        p("Hello sir".matches("^H[a-z]{3}o\\b.*$"));
        p("Hellosir".matches("^H[a-z]{1,9}sir$"));//此处没有单词边界

        //white lines
        p(" \n".matches("^[\\s&&[^\\n]]?\\n$"));

        //email address
        p("asdfdadfafd@afsdf.com".matches("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+"));//邮箱地址正则表达式

        //matches() find() lookingAt() start() end()
        //matches()方法去匹配字符串的时候当匹配到234后面的'-'字符时会返回一个false，然后find()方法会继续接着后面去查找符合条件的子串
        //如果有返回true如果没有返回false;这里需要强调的是每次调用matches()方法的时候都是重头去匹配，而find()会根据前面的条件去匹配
        //这里可以通过调用reset()方法重置匹配器，那么首次使用find()方法就会重最开始去执行；
        //find()是匹配满足条件的子串；
        //lookingAt()这个方法每次都是重头开始匹配；
        //start()方法返回以前匹配的初始索引；end()返回最后匹配字符之后的偏移量。(如果find()方法没有匹配到这两个方法通过无法使用会报错)

        Pattern p = compile("[234]{3,5}");
        String str = "234-2346-234-00";
        Matcher m = p.matcher(str);
        p(m.matches());
        //m.reset();
        p(m.find());
        p(m.start()+" "+m.end());
        p(m.find());
        p(m.start()+" "+m.end());
        p(m.find());
        //p(m.start()+" "+m.end());
        p(m.find());
        //p(m.start()+" "+m.end());

        p(m.lookingAt());
        p(m.lookingAt());
        p(m.lookingAt());
        p(m.lookingAt());

        //replaceyment CASE_INSENSITIVE:对大小写不敏感，这里就会将所有不区分大小写格式的字符串匹配，在匹配到子串的情况下
        //按奇数大写，偶数小写的格式放到StringBuffer里，最后打印出来；appendTail()是添加尾巴到StringBuffer里；
        Pattern pattern1 = compile("java",CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher("JAVA java jAvA JaVa jaVA JAva Java Hello world!");
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while(matcher1.find()){
            i++;
            if(i%2 == 0){
                matcher1.appendReplacement(sb,"java");
            }else {
                matcher1.appendReplacement(sb, "JAVA");
            }
        }
        matcher1.appendTail(sb);
        p(sb);

        //group 分组也是在匹配到子串的情况下进行的，通常是将需要进行分组的正则表达式用()分隔开，分组的时候按1，2,...依次分组，写在group()参数里；
        //其中什么不写默认是按正则表达式全部条件去查
        Pattern pattern2 = compile("([2345]{1,4})([a-z])");
        Matcher matcher2 = pattern2.matcher("12345a-2345b-23455c-123456d");
        while(matcher2.find()){
            p(matcher2.group(2));
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/pintec/Desktop/邮箱大全2016.htm"));
            String line = "";
            while((line = bufferedReader.readLine())!= null){
                Pattern pattern3 = compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
                Matcher matcher3 = pattern3.matcher(line);
                while(matcher3.find()){
                    p(matcher3.group());
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public static void p(Object o) {
        System.out.println(o);
    }
}
