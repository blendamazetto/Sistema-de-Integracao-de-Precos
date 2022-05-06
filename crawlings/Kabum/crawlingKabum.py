from re import S
from turtle import delay
from bs4 import BeautifulSoup
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager

class KabumCrawling:

    def __init__(self, flag, n = None):
        self.baseURL = "https://www.kabum.com.br/computadores/notebooks-ultrabooks/notebooks?page_number={}&page_size=20&facet_filters=&sort=most_searched"
        self.options = webdriver.ChromeOptions()
        self.options.use_chromium = True
        self.options.add_argument("headless")
        self.options.add_argument("disable-gpu")
        self.options.add_argument('log-level=3')
        if flag:
            self.proxy = 'http=85.26.146.169:80'
            self.options.add_argument(f'--proxy-server={self.proxy}')
        self.driver = webdriver.Chrome(ChromeDriverManager().install(), options=self.options)
        if n == None:
            self.driver.get(self.baseURL.format(1))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            aux = soup.find(id='listingPagination').find_all('a')
            self.pageQuantity = int(aux[-2].text)
        else:
            self.pageQuantity = n

    def extract_page_info(self, url):
        self.driver.get(url)
        soup = BeautifulSoup(self.driver.page_source, 'html.parser')
        result = {}
        try:
            result["descricao"] = soup.find('h1', {'itemprop' : 'name'}).text
        except:
            return None
        try:
            result["preco"] = soup.find('h4', {'itemprop' : 'price'}).text[3:]
        except:
            result["preco"] = ''
        try:
            info = soup.find(id= 'secaoInformacoesTecnicas')
        except:
            return None

        try:
            ps = info.find_all('p')
            result["marca"] = ps[1].text[9:]
            result["modelo"] = ps[2].text[10:]
        except:
            result["marca"] = ''
            result["modelo"] = ''

        try:
            section = soup.find(id= 'secaoAvaliacoes')
            result["rating"] = section.find('span').text
        except:
            result["rating"] = ''
        return result

    def extract_urls(self):
        result = []

        for page in range(1, self.pageQuantity + 1):
            print("pagina", page)
            self.driver.get(self.baseURL.format(page))
            soup = BeautifulSoup(self.driver.page_source, 'html.parser')
            aux = soup.find('div',{'class': 'sc-iqGgem ggYlfb'})
            for item in aux.main.find_all('a'):
                result.append('https://www.kabum.com.br' + item.get('href'))

        return result