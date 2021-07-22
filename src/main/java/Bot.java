import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.util.*;

public class Bot extends TelegramLongPollingBot {
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "rafsuher8584";
    public static final String DB_URL = "jdbc:postgresql://localhost:2707/testDB";
    Map<String, String> words = new HashMap<>();

    public static void setDictionary(Map<String, String> words) {
        words.put("car", "Say'yara(سيارة)");
        words.put("bag", "7eeba(حقيبة)");
        words.put("day", "yawm(يوم)");
        words.put("days", "2ayam(أيام)");
        words.put("monday", "al ethnayn(الإثنين)");
        words.put("tuesday", "athe’tholatha2(الثلاثاء)");
        words.put("wednesday", "al 2arbe3a(الأربعاء)");
        words.put("thursday", "al khamees(الخميس)");
        words.put("friday", "aj’jom3a(الجمعة)");
        words.put("saturday", "as’sabt(السبت)");
        words.put("sunday", "al 2a7ad(الأحد)");
        words.put("tomorrow", "bokra/ghadan(غداً / بكرا )");
        words.put("yesterday", "al bare7a(البارحة)");
        words.put("last", "al madeye/a(الماضي)");
        words.put("lunch", "ghada2(غداء)");
        words.put("dinner", "3asha2(عشاء)");
        words.put("breakfast", "fotoor(فطور)");
        words.put("noon", "theoher(ظهر)");
        words.put("afternoon", "ba3da ath’theoher(بعد الظهر)");
        words.put("dawn", "fajer(فجر)");
        words.put("sunrise", "shorooq(شروق)");
        words.put("sunset", "ghorrob(غروب)");
        words.put("time", "waqt(وقت)");
        words.put("first", "al awal(الأول)");
        words.put("second", "ath’thanye(الثّاني)");
        words.put("third", "ath’thaleth(الثّالث)");
        words.put("fourth", "ar’rabe3(الرّابع)");
        words.put("fifth", "al khames(الخامس)");
        words.put("sixth", "assades(السّادس)");
        words.put("seventh", "assabe3(السّابع)");
        words.put("eighth", "ath’thamen(الثّامن)");
        words.put("ninth", "at’tase3(التّاسع)");
        words.put("tenth", "al 3asher(العاشر)");
        words.put("before", "qabla(قبل)");
        words.put("after", "ba3ed(بعِد)");
        words.put("numbers", "arqam(أرْقَام)");
        words.put("number", "raqem(رقِمْ)");
        words.put("1", "wa7ad(وَاحَد)");
        words.put("2", "ethnan(إِثْنَان)");
        words.put("3", "thalatha(ثَلاثَة)");
        words.put("4", "arba3a(أرْبَعة)");
        words.put("5", "khamsa(خَمْسَة)");
        words.put("6", "setta(سِتّة)");
        words.put("7", "sab3a(سَبْعَة)");
        words.put("8", "thamaneya(ثَمَانية)");
        words.put("9", "tes3a(تِسْعَة)");
        words.put("10", "3ashra(عَشْرة)");
        words.put("11", "2a7ad 3ashar(أحد عشر)");
        words.put("12", "ethna 3ashar(إثنا عشر)");
        words.put("13", "thalathata 3ashar(ثلاثة عشر)");
        words.put("14", "arba3ata 3ashar(أربعة عشر)");
        words.put("15", "khamsata 3ashar(خمسة عشر)");
        words.put("16", "settata 3ashar(عشر ستة)");
        words.put("17", "sab3ata 3ashar(عشر سبعة)");
        words.put("18", "thamanyata 3ashar(عشر ثمانية)");
        words.put("19", "tes3ata 3ashar(تسعة عشر)");
        words.put("20", "3oshron(عُشْرون)");
        words.put("30", "thalathoon(ثلاثون)");
        words.put("40", "arba3oon(أربعون)");
        words.put("50", "khamsoon(خمسون)");
        words.put("60", "setton(ستون)");
        words.put("70", "sab3oon(سبعون)");
        words.put("80", "thamanoon(ثمانون)");
        words.put("90", "tes3oon(تسعون)");
        words.put("100", "me2a/meyye(مئة)");
        words.put("1:00", "assa3a al wa7eda(الساعة الواحدة)");
        words.put("2:00", "assa3a ath’thaneya(الساعة الثانية )");
        words.put("3:00", "assa3a ath’thaletha(الساعة الثالثة)");
        words.put("4:00", "assa3a ar’rabe3a(الساعة الرّابعة)");
        words.put("5:00", "assa3a al khamesa(الساعة الخامسة)");
        words.put("6:00", "assa3a as’sadesa(الساعة السادسة)");
        words.put("7:00", "assa3a as’sabe3a(الساعة السابعة)");
        words.put("8:00", "assa3a ath’thamena(الساعة الثامنة)");
        words.put("9:00", "assa3a at’tase3a(الساعة التاسعة )");
        words.put("10:00", "assa3a al 3ashera(الساعة ة العاشر)");
        words.put("11:00", "assa3a al 7adeya 3ashar(الساعة الحادية عشر)");
        words.put("12:00", "assa3a ath’thaneya 3ashar(الساعة الثانية عشر)");
        words.put("200", "me2atan/metain(مئتان)");
        words.put("300", "thaltha me2a(ثلاثمئة)");
        words.put("400", "arb3o ma2a(مئة أربعة)");
        words.put("500", "khamsome2a(مئة خمس)");
        words.put("600", "setoma2a(مئة ستة)");
        words.put("700", "sab3o me2a(مئة سبعة)");
        words.put("800", "thamanya me2a(مئة ثمانية)");
        words.put("900", "tes3a me2a(مئة تسعة)");
        words.put("1000", "alf(ألف)");
        words.put("i", "ana(أَنَا)");
        words.put("you", """
                For male: anta(أَنْت)
                For female: ante(أَنْت)
                Plural: ento(إنْتُو)""");
        words.put("we", "na7no(نَحْن)");
        words.put("she", "heya(هِيَ)");
        words.put("he", "howa(هُوَ)");
        words.put("they", "hom(هم)");
        words.put("this", "For male: hathea(هَذا)\n" +
                "For female: hatheehe(هَذه)");
        words.put("that", "For male:thealeka(ذَلِك)\n" +
                "For female: telka(َتِلْك)");
        words.put("these", "For non-human: hatheehe(هَذه)\n" +
                "For human: haa2ola2(هؤلاء)");
        words.put("those", "For non-human: telka(تِلْك)\n" +
                "For human: 2ola2eka(أُولَئكَ)");
        words.put("in", "fe(في)");
        words.put("on", "3ala(على)");
        words.put("in front of", "amama(أمام)");
        words.put("behind", "khalfa(خلف)");
        words.put("under", "ta7ta(تحت)");
        words.put("above", "fawqa(فوق)");
        words.put("between", "bayna(بين)");
        words.put("from", "men(من)");
        words.put("to", "ela(إلى)");
        words.put("next to", "bejaneb(بجانب)");
        words.put("left", "yasar(يسار)");
        words.put("right", "yameen(يمين)");
        words.put("what", "ma/mathea(ما \\ماذا)");
        words.put("when", "mata(متى)");
        words.put("where", "ayna(أين)");
        words.put("why", "lemathea(لماذا)");
        words.put("how", "kayfa(كيف)");
        words.put("which", "ayya(أيّ)");
        words.put("who", "man(منْْ)");
        words.put("whom", "meen(ميين)");
        words.put("office", "maktab(مَكْتَب)");
        words.put("library", "maktaba(مَكْتَب)");
        words.put("school", "madrasa(مدرسة)");
        words.put("embassy", "safara(سفارة)");
        words.put("palace", "qaser(قصر)");
        words.put("mosque", "masjed(مسجد)");
        words.put("house", "bayt(بيت)");
        words.put("home", "bayt(بيت)");
        words.put("hotel", "otail(أوتيل)");
        words.put("restaurant", "mat3am(مطعم)");
        words.put("factory", "masna3(مصنع)");
        words.put("university", "jame3a(جامعة)");
        words.put("department", "qesem(قسم)");
        words.put("village", "qarya(قرية)");
        words.put("garden", "7adeeqa(حديقة)");
        words.put("street", "shar3e(شّارع)");
        words.put("bank", "bank(بنك)");
        words.put("cafe", "cafe(كافيه)");
        words.put("museum", "mat7af(متحف)");
        words.put("cinema", "cinema(سينما)");
        words.put("market", "sook(سّوق)");
        words.put("playing field", "mal3ab(مَلْعبْ)");
        words.put("city", "madina(مدينة)");
        words.put("country", "dawla(دولة)");
        words.put("england", "engiltra(إنكلترا)");
        words.put("lebanon", "lobnan(لبنان  )");
        words.put("france", "faransa(فرنسا)");
        words.put("spain", "esbanya(إسبانيا)");
        words.put("italy", "italya(إيطاليا)");
        words.put("britain", "bretanya(بريطانيا )");
        words.put("america", "amerka(أميركا )");
        words.put("russia", "rosya(روسيا)");
        words.put("kazakhstan", "kazakhstan(كازخستان  )");
        words.put("germany", "almanya(ألمانيا )");
        words.put("teacher", "modarres(a)(مُدرس)");
        words.put("teachers", "modarresoon/een(مُدرِّسون/ مُدرِّسين)");
        words.put("engineer", "mohandes(a)(مُهَنْدس)");
        words.put("engineers", "mohandeson/een(مُهندسون/ مهندسين)");
        words.put("nurse", "momar'red(a)(ممرض)");
        words.put("nurses", "momar'redon(ممرضون)");
        words.put("accountant", "mo7aseb(a)(محاسب)");
        words.put("accountants", "mo7aseboon/een(محاسبين\\ مُحاسبون)");
        words.put("carpenter", "naj'jar(a)(نجار)");
        words.put("carpenters", "naj'jaroon/een(نجارون \\نجارين)");
        words.put("baker", "khab'baz(a)(خباز)");
        words.put("bakers", "khab'bazoon/een(خبازون/خبازين)");
        words.put("fisherman", "say'yad(at) samak(صيّاد)");
        words.put("fishermen", "say'yadon/een samak(صيادون/صيادين)");
        words.put("writer", "kateb(كاتِبْ)");
        words.put("player", "la3eb(لاعِبْ)");
        words.put("ambassador", "safeer(سَفِيرْ)");
        words.put("traveler", "mosafer(مُسَافِر)");
        words.put("student", "taleb(طالب)");
        words.put("retired", "motaqa3ed(متقاعد)");
        words.put("nationality", "jenseya(جنسيّة)");
        words.put("lebanese", "lobnany-e,a(لبناني )");
        words.put("french", "faransy-e/a(فرنسي)");
        words.put("spanish", "esbany-e/a(إسباني)");
        words.put("british", "bretany-e/a(بريطاني)");
        words.put("italian", "italy-e/a(إيطالي)");
        words.put("iranian", "3eraq-e/a(عراقي)");
        words.put("american", "amerky-e/a(أميركي)");
        words.put("russian", "roosy-e/a(روسي)");
        words.put("kazakh", "kazakhy-e/a(كزخي )");
        words.put("weather", "Al attaqs(الطّقس)");
        words.put("atmosphere", "Al jaw(الجو)");
        words.put("temperature", "darajt al 7arara(درجةالحرارة)");
        words.put("temperatures", "darajat al 7arara(درجات الحرارة)");
        words.put("cold", "bared/bard(بارد\\برد)");
        words.put("hot", "7ar/shoob(حار\\شوب)");
        words.put("sun", "shams(شمس)");
        words.put("sunny", "moshmes(مُشْمِسْ)");
        words.put("snow", "thalj(ثلجْ)");
        words.put("snowy", "mothlej(مُثلِج)");
        words.put("clouds", "ghoyom(غيوم)");
        words.put("cloudy", "gha2em(غائم)");
        words.put("winds", "reya7(رياح)");
        words.put("windy", "3asef(عاصف)");
        words.put("stormy", "3asef(عاصف)");
        words.put("storm", "3asefa(عاصفة)");
        words.put("thunders", "ra3ed(رعد)");
        words.put("thunderstorm", "3asefa ra3deya(عاصفة رعديّة)");
        words.put("rain", "matar(مطر)");
        words.put("rainy", "momter(مُمطر)");
        words.put("fog", "dabab(ضباب)");
        words.put("foggy", "dababy(ضبابي)");
        words.put("season", "fasel(فصل)");
        words.put("seasons", "fosool(فصول)");
        words.put("winter", "sheta2(شتاء)");
        words.put("spring", "rabee3(ربيع)");
        words.put("summer", "sayf(صيف)");
        words.put("autumn", "khareef(خريف)");
        words.put("he played", "La3eba(لَعِبَ)");
        words.put("he worked", "3amela(عمل)");
        words.put("he ate", "akala(أكَلَ)");
        words.put("he studied", "darasa(دَرَسَ)");
        words.put("he left", "taraka(تَرَكَ)");
        words.put("he started", "bada2a(بَدَأَ)");
        words.put("he went", "theahaba(ذَهَبَ)");
        words.put("he did", "fa3al(فَعَلَ)");
        words.put("he ran", "rakada(ركَضَ)");
        words.put("he wrote", "kataba(كَتَبَ)");
        words.put("he travelled", "safara(سَافَرَ)");
        words.put("he broke", "kasara(كَسَر)");
        words.put("he got", "7asala(حصل)");
        words.put("he said", "qāla(قال)");
        words.put("beautiful", "jamīl(جميل)");
        words.put("ugly", "qabīh(قبيح)");
        words.put("new", "jadīd(جديد)");
        words.put("old", "qadim(قديم)");
        words.put("heavy", "thaqīl(ثقيل)");
        words.put("light", "khafīf(خقيف)");
        words.put("big", "kabīr(كبير)");
        words.put("large", "kabīr(كبير)");
        words.put("bad", "sai(سي)");
        words.put("exciting", "mutheer(مثير)");
        words.put("small", "sagheer(صّغير)");
        words.put("comfortable", "moree7(مريح)");
        words.put("good", "jayed(جيد)");
        words.put("very", "jeddan(جدان)");
        words.put("very good ", "jayed jeddan(جيد جدا)");
        words.put("clever", "mojtahed(مجتهد)");
        words.put("shy", "khajool(خجول)");
        words.put("short", "qaseer(قصير)");
        words.put("long", "taweel(طويل)");
        words.put("important", "mohem(مهم)");
        words.put("suitable", "monaseb(مناسب)");
        words.put("white", "For male: abyad(أبْيَض)\n" +
                "For female: baydaa(بَيْضاء)");
        words.put("Red", "For male: 27mar(أحْمَر)\n" +
                "For female: 7amraa(حَمْراء)");
        words.put("blue", "For male: 2zraq(أزْرَق)\n" +
                "For female: zarqaa(زَرْقاء)");
        words.put("green", "For male: 2khdar(أَخْضَرْ)\n" +
                "For female: khadraa(خَضْراء)");
        words.put("black", "For male: aswad(أسْود)\n" +
                "For female: sawdaa(سَوْداء)");
        words.put("yellow", "For male: asfar(أَصْفر)\n" +
                "For female: safraa(صَفْراء)");
        words.put("pink", "For male: zahre(زهري)\n" +
                "For female: zahreya(زهرية)");
        words.put("brown", "For male: bonne(بُنّي)\n" +
                "For female: bonneya(بنيّة)");
        words.put("gray", "For male: remade(رمادي)\n" +
                "For female: remadeya(رماديّة)");
        words.put("silver", "For male: fedde(فضّي)\n" +
                "For female: feddeya(فضيّة)");
        words.put("gold", "For male: theahabe(ذهبي)\n" +
                "For female: theahabeya(ذهبيّة)");
        words.put("purple", "For male: banafsaje(بيفسجيّ)\n" +
                "For female: banafsajeya(بيفسجيّة)");
        words.put("orange", "For male: bortoqale(برتقالي)\n" +
                "For female: bortoqaleya(برتقالية)");
        words.put("needle", "ibra(إبرة)");
        words.put("door", "ba:b(باب)");
        words.put("palm", "iad(يد)");
        words.put("book", "kitab(كتاب)");
        words.put("internet", "intarnat(إنترنت)");
        words.put("earthquake", "zælzæl(زلزال)");
        words.put("radio", "radīu(راديو)");
        words.put("game", "lo3ba(لُعْبَة)");
        words.put("toy", "lo3ba(لُعْبَة)");
        words.put("games", "al3aab(ألْعاب)");
        words.put("journey", "safar(سَفَر)");
        words.put("study", "derasa(دِرَاسَة)");
        words.put("boy", "walad(ولد)");
        words.put("girl", "bent(بنت)");
        words.put("table", "tawela(طاولة)");
        words.put("mother", "2om(أم)");
        words.put("window", "shob'bak(شباك)");
        words.put("chair", "korse(كرسي)");
        words.put("meeting", "ejtema3(إجتماع)");
        words.put("hand", "yad(يد)");
        words.put("moon", "qamar(قمر)");
        words.put("eye", "3ayn(عينٌ)");
        words.put("woman", "imra'e(امرأة)");
        words.put("aunt", "From mother's side: khala(خالة)\n" +
                "From father's side: 3amma(عمّة)");
        words.put("uncle", "Form mother's side: khal(خال)\n" +
                "From father's side: 3am(عم)");
        words.put("pen", "qalam(قلم)");
        words.put("picture", "soora(صورة)");
        words.put("flower", "ward(ورد)");
        words.put("father", "2ab(أب)");
        words.put("rock", "sakhra(صخرة)");
        words.put("peach", "khookh(خوخ)");
        words.put("giraffe", "zarafa(زرافة)");
        words.put("dog", "kalb(كلب)");
        words.put("holiday", "3ala 3otla(على عطلة)");
        words.put("name", "esem(إسم)");
        words.put("bottle", "zojaja(زجاجة)");
        words.put("language", "logha(لغة)");
        words.put("animal", "7ayawan(حيوان)");
        words.put("computer", "cumibyutir(كومبيوتر)");
        words.put("phone", "telefon(تلفون)");
        words.put("shirt", "qamees(قميص)");
        words.put("man", "rajol(رجل)");
        words.put("river", "naher(نهر)");
        words.put("message", "resala(رسالة)");
        words.put("report", "taqreer(تقرير)");
        words.put("conference", "mo2tmar(مؤتمر)");
        words.put("notes", "mola7atheat(ملاحظات)");
        words.put("now", "al2an(الآن)");
        words.put("at the moment", "fe hathehe alla7thea(في هذه اللحظة)");
        words.put("anywhere", "ayyo makan(أيُّ مكان)");
        words.put("i was born", "woledto(ولدتُ)");
        words.put("you were born", "woledta/te/too(ولدتوا \\تَ\\تِ)");
        words.put("he was born", "woleda(وُلِدَ)");
        words.put("she was born", "woledat(ولدتْ)");
        words.put("they were born", "woledoo(ولِدوا)");
        words.put("we were born", "woledna(ولدنا)");
        words.put("must", "yajeb 2an(أن يجب)");
        words.put("have to", "yajeb 2an(يجبأن)");
        words.put("should", "yajeb 2an(يجب أن)");
        words.put("might", "yomkeno 2an(يُمْكِنُ أن)");
        words.put("topic", "mawdoo3(موضوع)");
        words.put("child", "For male: tefel(طِفِل)\n" +
                "For female: tefla(طفلة)");
        words.put("children", "atfal(أطفال)");
        words.put("verb", "fe3el(فِعِل)");
        words.put("he plays", "yal3abo(يَلْعَبُ)");
        words.put("he works", "ya3malo(يعْمَلُ)");
        words.put("he eats", "ya2kolo(يَأْكُلُ)");
        words.put("he studies", "yadroso(يدْرُسُ)");
        words.put("he leaves", "yatroko(يتْرُكُ)");
        words.put("he goes", "yathehabo(يذْهَبُ)");
        words.put("he does", "yaf3alo(يفْعَلُ)");
        words.put("he runs", "yarkodo(يرْكُضُ)");
        words.put("he writes", "yaktobo(يكتُب)");
        words.put("he travels", "safara(سَافَرَ)");
        words.put("he brakes", "kasara(كَسَر)");
        words.put("he gets", "7asala(حصل)");
        words.put("he speaks", "yataklamo(يتكلم)");
        words.put("work", "3amal(عمل)");
        words.put("football", "qorat qadam(كُرَة قَدَمْ)");
        words.put("i have", "3endy/ladayya(عندي /لديّ)");
        words.put("you have", "endoka/3endoke/ladayka/ladayke(عندكَ/عندُكِ/لديكَ/لديكِ)\n " +
                "plural - endakom/ladaykom(عندكم/لديكم)");
        words.put("he has", "endaho/ladayhe(عندَهُ/لديه)");
        words.put("she has", "endaha/ladayha(لديها/عندها)");
        words.put("they have", "endahom/ladayhom(عندهم/لديهم)");
        words.put("we have", "endana/ladayna(عندنا /لدينا)");
        words.put("he got up", "estayqathea(إستيقظ)");
        words.put("he gets up", "yastayqetheo(يسْتيْقظُ)");
        words.put("he arrived", "wasala(وصلَ)");
        words.put("he arrives", "yaselo(يصِلُ)");
        words.put("he found", "wajada(وجدَ)");
        words.put("he finds", "yajedo(يجِدُ)");
        words.put("he was", "kana(كانَ)");
        words.put("i am", "2akoono(أكونُ)");
        words.put("you are", "takono/neen(تكون\\نين)\n " +
                "plural - takonoon(تكونون)");
        words.put("we are", "nakoono(نكونُ)");
        words.put("they are", "yakonoon(يكونون)");
        words.put("she is", "takoon(تكونْ)");
        words.put("he is", "yakono(يكُونُ)");
        words.put("i was not", "lam akon(لم أكن)");
        words.put("you were not", "lam takon/takooneen(لم تكنْ\\ تكونين)");
        words.put("she was not", "lam takon(لم تكنْ)");
        words.put("they were not", "lam yakoono(لم يكونوا)");
        words.put("we were not", "lam nakon(لم نكن)");
        words.put("i wasn't", "lam akon(لم أكن)");
        words.put("you weren't", "lam takon/takooneen(لم تكنْ\\ تكونين)");
        words.put("she wasn't", "lam takon(لم تكنْ)");
        words.put("they weren't", "lam yakoono(لم يكونوا)");
        words.put("we weren't", "lam nakon(لم نكن)");
        words.put("he invited", "da3a(دعا)");
        words.put("he invites", "yad3o(يدْعُو)");
        words.put("he walked", "masha(مشى)");
        words.put("he walks", "yamshe(يمْشِي)");
        words.put("he threw", "rama(رمى)");
        words.put("he throws", "yarme(يرْمِي)");
        words.put("he complained", "shaka(شكا)");
        words.put("he complains", "yashko(يشْكُو)");
        words.put("he gave", "3ata(عطى)");
        words.put("he gives", "ya3te(يعْطِي)");
        words.put("happy birthday", "3eed milad sa3eed(عيد ميلاد سعيد)");
        words.put("happy new year", "ana sa3eeda w jadeeda(سنة سعيدة و جديدة)");
        words.put("he slept", "nama(نامَ)");
        words.put("he sleeps", "yanamo(ينامُ)");
        words.put("he finished", "entaha(إنتهى)");
        words.put("he finishes", "yantahee(ينتهي)");
        words.put("cheeseburger", "burger jobna(برجر جبنة)");
        words.put("tomatoes", "banadora(بندورة)");
        words.put("fish", "samak(سمك)");
        words.put("chicken", "jaj(جاج)");
        words.put("meat", "la7me(لحمة)");
        words.put("carrots", "jazar(جزر)");
        words.put("rice", "roz(رز)");
        words.put("birds", "toyoor(طيور)");
        words.put("medicine", "dawaa2(دواء)");
        words.put("guests", "doyoof(ضيوف)");
        words.put("airplane", "atta2era(الطائرة)");
        words.put("water", "may(مي)");
        words.put("butter", "zebda(زبدة)");
        words.put("milk", "7aleeb(حليب)");
        words.put("cheese", "jobna(جبنة)");
        words.put("bread", "khobez(خبز)");
        words.put("coffee", "qahwa(قهوة)");
        words.put("jam", "mrabba(مربّى)");
        words.put("sugar", "sokkar(سُكر)");
        words.put("salt", "mele7(ملح)");
        words.put("cup", "koob(كوب)");
        words.put("glass", "kassa(كاسة)");
        words.put("apple", "tofa7a(تفاحة)");
        words.put("banana", "mawza(موزة)");
        words.put("he ordered", "talaba(طلب)");
        words.put("juice", "3aseer(عصير)");
        words.put("sandwich", "sandweesha(سندويشة)");
        words.put("basketball", "qorat salla(كرة سلة)");
        words.put("key", "mofta7(مُفتاح)");
        words.put("never", "abadan(أبدا)");
        words.put("always", "dae2man(دائما)");
        words.put("sometimes", "2ayanan(أحياناً)");
        words.put("often", "ghaleban(غالبا)");
        words.put("usually", "2adatan(عادةً)");
        words.put("hardly ever", "naderan ma(نادراً ما)");
        words.put("january", "kanoon att'thane(كانون الثاني)");
        words.put("february", "shbat(شباط)");
        words.put("march", "athear(أذار)");
        words.put("april", "nisan(نيسان)");
        words.put("may", "month - ayar(أيار)\n" +
                "modal - robama(ربما)");
        words.put("june", "7ozayran(حزيران)");
        words.put("july", "tamooz(تموز)");
        words.put("august", "2ab(آب)");
        words.put("september", "aylool(أيلول)");
        words.put("october", "teshreen awal(تشرين اول)");
        words.put("november", "teshreen thanye(تشرين ثاني)");
        words.put("december", "kanoon awal(كانون أول)");
        words.put("how much", "kam(كم)");
        words.put("how many", "kam(كم)");
        words.put("people", "ashkhas(أشخاص)");
        words.put("person", "shakhes(شخص)");
        words.put("chocolate", "chocola(شوكول)");
        words.put("flour", "ta7een(طحين)");
        words.put("there is", "yowjad honaka(يوجد هناك)");
        words.put("there are", "yowjad honaka(يوجد هناك)");
        words.put("each", "kol(كل)");
        words.put("every", "kolla(كل)");
        words.put("eggs", "bayd(بيض)");
        words.put("fridge", "barad(براد)");
        words.put("furniture", "3afesh(عفِش)");
    }

    @Override
    public String getBotUsername() {
        return "arabicTranslatorBot";
    }

    @Override
    public String getBotToken() {
        return "1720187822:AAHz6PEA0cgdD8T5RFGYdOiHij6dB44bG0I";
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/translate"));
        keyboardSecondRow.add(new KeyboardButton("/description"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void translate(Message message) {
        String SQL_GET_TRANSLATION = "select transcription, arabic from dictionary where english = ?";
        StringBuilder result = new StringBuilder();
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("create table dictionary ( id serial primary key, english  varchar, transcription varchar, arabic varchar);");
            statement.execute("insert into dictionary(english, transcription, arabic) " +
                    "values ('chair', 'korsi', '*arabic word for chair*');");
            preparedStatement = connection.prepareStatement(SQL_GET_TRANSLATION);
            preparedStatement.setString(1, message.getText().toLowerCase(Locale.ROOT));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result.append(resultSet.getString("transcription"))
                        .append("(")
                        .append(resultSet.getString("arabic"))
                        .append(")");
                sendMsg(message, result.toString());
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        sendMsg(message, "К сожалению, у меня нет перевода этого слова");

        /*setDictionary(words);
        String engWord = message.getText();
        sendMsg(message, words.getOrDefault(engWord.toLowerCase(Locale.ROOT), "К сожалению, у меня нет перевода этого слова"));*/
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start" -> sendMsg(message, """
                        Привет\uD83D\uDC4B, скорее всего ты студент KILC'a? Ведь именно для них я предназначен, а точнее для тех, кто учит арабский. Создатель этого бота устал от постоянного поиска нужного перевода в миллионах файлах и чтобы облегчить свою и жизнь других студентов, он решил создать меня.
                        \s
                        Ниже описан мой функционал:
                        \s
                        /translate - Введи слово на английском и я выведу его перевод на арабском. Я работаю как словарь, и не смогу перевести предложения, но могу перевести некоторые словосочетания.
                        /description - Немного о создателе бота
                        """);
                case "/command1", "/translate" -> sendMsg(message, """
                        Введи слово, которое нужно перевести.\s
                        1) Для перевода чисел и времени пиши цифрами.
                        Пример: 1; 15; 4:00
                        2) Для перевода глаголов пиши перед глаголами местоимение "he".
                        Пример: he wrote; he speaks""");
                case "/command2", "/description" -> sendMsg(message, """
                        Создатель бота, студент третьего курса - Андасов Рустем. Если хочешь предложить новые идеи или нашел ошибку, можешь связаться со мной по контактам ниже:
                        \s
                        Instagram: @andasov.03
                        Email: rustemandasov6@gmail.ru""");
                default -> translate(message);
            }
        }
    }
}