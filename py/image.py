import json
import urlparse
import re  
import sys,codecs
import urllib  
from bs4 import BeautifulSoup
#coding:gbk2312
  
def getHtml(url):  
    page = urllib.urlopen(url)  
    html = page.read()  
    return html  
  
def getImg(html):  
    reg = r'src="(.*?\.jpg)" pic_ext'  
    imgre = re.compile(reg)  
    imglist = re.findall(imgre,html)  
    x=0  
    for imgurl in imglist:  
        urllib.urlretrieve(imgurl,'%s.jpg' % x)  
	print imgurl
        x+=1  
    return x;  

def getItem(html):
    reg1 = r'\<div class="menu__items"\>\<table.*?\>(.*?)\</table\>';
    reg = r'td class=".*?"\>(.*?)\<span';
    itemsre = re.compile(reg1)
    itemslist=re.findall(itemsre,html);

    itemre = re.compile(reg)
    x=0
    for items in itemslist:
         itemlist=re.findall(itemre,items)
         
         for item in itemlist:
             x+=1
             print item

    return x

def getShangJia(html):
    reg=r'link f3".*?href="(.*?)#smh:.*?\</a\>'
    urlreg=re.compile(reg)
    urllist=re.findall(urlreg,html)
    x=0    
    for url in urllist:
        #print url[0]+' '+unicode(url[1], 'gbk');
        print url
        x+=1
    return x

def fun():
	#html = getHtml("http://tieba.baidu.com/p/2460150866")  
	#html = getHtml("http://www.meituan.com/shop/18620")

	#html = getHtml("http://hz.meituan.com/shop/707799")
	html=getHtml("http://hz.meituan.com/category/meishi")
		#print html
	return html
	#print getShangJia(html)
	#print getItem(html)

def convert(str):
	if not isinstance(str, unicode):
		str = unicode(str, 'utf-8')
	return str.encode('utf-8');

def getNextPage(soup):
	paginator = soup.find('div',{'class':'paginator-wrapper'})
	next = paginator.find('li',{'class':'next'})
	url = next.a.get('href')
	baseurl='http://hz.meituan.com/category/meishi'
	return urlparse.urljoin(baseurl, url);

def func():
	html=fun()
	soup=BeautifulSoup(html, 'html.parser')
	print getNextPage(soup)

def saveToJSON(merchants):
	output = open('merchants.info', 'w')
	
	output.writelines(convert(json.dumps(merchants,ensure_ascii=True,indent=1, encoding='utf-8')))
	
	output.close()
	
def main():
	
	html=fun()
	soup=BeautifulSoup(html, 'html.parser')

	merchants=[]

	div = soup.find_all('div',{'class': 'poi-tile-nodeal'})
	
	for d in div:
		cf = d.find('div', {'class':'basic cf'})
		
		merchant = {}	
		merchant['name'] = convert(cf.a.get_text());
		merchant['url']=cf.a.get('href').split('#')[0]

		tagList=d.find('div',{'class':'tag-list'})
		tags=[]
		for tag in tagList.find_all('a'):
			tags.append(convert(tag.get_text()))

		merchant['tag'] = tags;
		
		rate= d.find('div',{'class':'rate'}).find('span',{'class':'num'}).get_text()
		merchant['rate'] = int(rate);

		money = d.find('div',{'class':'poi-tile__money'})
		avg = money.find('span',{'class':'price'})

		if avg is not None:
			average= convert(avg.get_text())
			merchant['avg']=average

		price= money.find('span',{'value'}).get_text().split(';')[-1]

		merchant['price'] = price
		
		merchants.append(merchant);
	
	
	saveToJSON(merchants)
	
main() 
