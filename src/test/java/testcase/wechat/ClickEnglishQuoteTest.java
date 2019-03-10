package testcase.wechat;

import framework.BaseTest;
import framework.Driver;
import org.testng.annotations.Test;
import page.wechat.*;
import static framework.ResourceUtil.getRes;

public class ClickEnglishQuoteTest  extends BaseTest {
    @Test
    public class ShowMyMomentTest extends BaseTest {

        //运行前需先手动关注公从号 "英语短句每日分享"
        public void traverseEnglishQuote(){

            String name = "英语短句每日分享";

            WeiXinMainPage.verify()
                    .clickContactButton();

            WeiXinContactPage.verify()
                    .clickOfficialAccountButton();

            WeiXinOfficialAccountsPage.verify()
                    .clickSearchButton();

            WeiXinSearchPage.verify()
                    .clickSearchText()
                    .inputText(name)
                    .clickFirstSearchResult(getRes("SEARCH_PAGE_SEARCH_RESULT_CLASS"), name);

            WeiXinSubscriptionPage.verify()
                    .clickHistoryButton();

            int x = Driver.getDeviceWidth()/2;
            int y = Driver.getDeviceHeight() - 100;

            int adClickedCount = 0;
            int articleFailureCount = 0;
            int adFailureCount = 0;

            boolean articleClicked;

            int distance = 75;
            int step = 80;

            while(true){
                WeiXinArticleListPage.verify();

                //Scroll to show old articles to be clicked
                Driver.scrollUp(x, y, 200);
                Driver.sleep(5);



                log.info("Trying to click Article");
                Driver.clickByCoordinate(x, y - distance);

                //Make sure article is clicked
                try {
                    WeiXinArticlePage.verify()
                            .clickAD();
                    distance = 75;
                }catch (Exception e){
                    log.info("Fail to click Article!!!");
                    distance = distance + step;
                    articleFailureCount ++;
                    Driver.takeScreenShot();
                    continue;
                }

                boolean containEnglishQuote = Driver.getPageSource().contains(getRes("ENGLISH_QUOTE_NAME_TEXT"));

                //Check if enter into AD Page
                if(containEnglishQuote){
                    Driver.takeScreenShot();
                    Driver.pressBack();
                    adFailureCount ++;
                    continue;
                }

                //Enter into AD Page, do some scroll
                for(int i = 0 ; i <2 ; i++) {
                    Driver.sleep(5);
                    //Driver.sleep(rnd.nextInt(maxSleepTime));
                    Driver.scrollUp(x, y, y);
                }

                log.info("====Succeed in clicking AD. Press back twice");
                Driver.pressBack();
                Driver.pressBack();
                adClickedCount ++;

                log.info("AD clicked: " + adClickedCount + "    Article Failure clicked:" +articleFailureCount + "  AD Failure clicked:" + adFailureCount);

            }

            //Test end
        }
    }
}
