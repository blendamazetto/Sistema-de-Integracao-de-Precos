import json
import time
from crawlingMagalu import MagaluCrawling    

def main():
    start = time.time()
    crawling = MagaluCrawling(True)
    urls = crawling.extract_urls()
    results = []

    for url in urls:
        result = crawling.extract_page_info(url)
        if result != None:
            result["url"] = url
            results.append(result)
                
    with open('resultsMagazineLuiza.json', 'w', newline='', encoding='utf-8') as f:
        f.write(json.dumps(results, indent=4))           
    print("Crawling finalizado em: ", time.time() - start, " segundos")


if __name__ == "__main__":
    main()