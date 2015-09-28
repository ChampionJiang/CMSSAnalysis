import re  
import urllib  
from bs4 import BeautifulSoup
#coding=utf-8
  
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
#html = getHtml("http://tieba.baidu.com/p/2460150866")  
#html = getHtml("http://hz.meituan.com/shop/2410992")

#html = getHtml("http://hz.meituan.com/shop/707799")
html=getHtml("http://hz.meituan.com/category/meishi")
#print html
#print getShangJia(html)

soup=BeautifulSoup(html, 'html.parser')
#print html
#print soup.title
#print soup.title.name
#print soup.p
div = soup.find_all('div',{'class': 'poi-tile-nodeal'})

#print div
n=0
for d in div:
   print d
   n=n+1
print n
#lis=div.findAll('li', {'class': 'next'})

pattern=r'href="(.*?)"'
urlreg=re.compile(pattern)
#for li in lis:
 #   link=li.find('a')
    
  #  url=link['href']
   # print url;

#pattern=r'href="(.*?)"'
#urlreg=re.compile(pattern)
#urls=re.findall(urlreg,div)

#for url in urls:
 #   print url

#for line in div:
   # print line
#print soup.prettify()  