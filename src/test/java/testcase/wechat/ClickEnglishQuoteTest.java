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


            while(true){
                WeiXinArticleListPage.verify();

                //Scroll to show old articles to be clicked
                Driver.scrollUp(x, y, 200);
                Driver.sleep(5);

                int startY = 75;
                int step = 100;

                //Click at most 3 times till article is clicked.
                for(int i = 0; i<3 ; i++) {
                    Driver.clickByCoordinate(x, y - startY);
                    try {
                        WeiXinArticlePage.verify()
                                .clickAD();

                        Driver.pressBack();
                        Driver.pressBack();
                        //Driver.sleep(10);
                        i=3;
                    }catch (Exception e){
                        startY = startY - step;
                    }
                }
            }

            //Test end
        }
    }
}
