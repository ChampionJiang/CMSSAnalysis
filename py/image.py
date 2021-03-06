#https://raw.githubusercontent.com/pypa/pip/master/contrib/get-pip.py
#coding=utf-8
import json
import urlparse
import re  
import sys,codecs
import urllib  
import urllib2
import time
from bs4 import BeautifulSoup
  
def getHtml(inputurl):
	print inputurl
	header={'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
	req = urllib2.Request(url = inputurl, headers=header)
	html = urllib2.urlopen(req).read()
	#html = urllib.urlopen(inputurl).read()
	#print html
	return html  
  
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
	return str

def getNextPage(html):
	soup=BeautifulSoup(html,'html.parser')
	paginator = soup.find('div',{'class':'paginator-wrapper'})
	next = paginator.find('li',{'class':'next'})
	if next==None:
		return None;

	url = next.a.get('href')
	baseurl='http://hz.meituan.com/category/meishi'
	return urlparse.urljoin(baseurl, url);

def func():
	html=fun()
	print getNextPage(html)

def saveToJSON(merchants):
	output = codecs.open('merchants.info','w')
	output.writelines(json.dumps(merchants, ensure_ascii=False, indent=4).encode('utf-8'))
	output.close()
	return	
	
def crawleMerchantsInfo(html, merchants):
	soup=BeautifulSoup(html, 'html.parser')

	div = soup.find_all('div',{'class': 'poi-tile-nodeal'})
	
	for d in div:
		cf = d.find('div', {'class':'basic cf'})
		
		merchant = {}	
		merchant['name'] = convert(cf.a.get_text().strip());
		merchant['url']=cf.a.get('href').split('#')[0].strip()

		tagList=d.find('div',{'class':'tag-list'})
		tags=[]
		for tag in tagList.find_all('a'):
			tags.append(convert(tag.get_text().strip()))

		merchant['tag'] = tags;
		
		rate= d.find('div',{'class':'rate'}).find('span',{'class':'num'}).get_text().strip()
		#merchant['rate'] = int(rate);

		money = d.find('div',{'class':'poi-tile__money'})
		avg = money.find('span',{'class':'price'})

		if avg is not None:
			average= convert(avg.get_text().strip())
			merchant['avg']=average

		price= money.find('span',{'value'}).get_text().split(';')[-1]

		merchant['price'] = price
		
		merchants.append(merchant);
	
def crawleMerchantInfo(html, merchant):
	soup=BeautifulSoup(html, 'html.parser')
	summary=soup.find('div',{'class':'summary biz-box fs-section cf'})
	leftsection=summary.find('div',{'class':'fs-section__left'})
	reg=r'\<p.*?\>(.*?)\</p\>'
	phonereg=re.compile(reg)
	for p in leftsection.findAll('p'):
		s=str(p)
		if re.match(phonereg,s) is not None:
			merchant['phone']=p.get_text().strip()
		else:
			merchant['address']=convert(p.find('span',{'class':'geo'}).get_text().strip())
		
	rightsection=summary.find('div',{'class':'fs-section__right'})
	bizlevel=rightsection.find('span',{'class':'biz-level'}).get_text().strip()
	merchant['bizlevel']=bizlevel

	counts=rightsection.find('div',{'class':'counts'}).findAll('div')
	merchant['consume_num'] = int(counts[0].get_text().strip().split()[-1])
	merchant['rate_num']= int(counts[1].get_text().strip().split()[-1])	
	
	items=soup.find('div',{'class':'menu__items'})
	if items is not None:
		itemslist = items.findAll('table')[0].get_text()
		merchant['special'] = itemslist.split()

	ratelist=soup.find('div',{'class':'ratelist-content cf'})
	
	if ratelist is not None:
		reviews = []
		numreg=re.compile(r'(\d+)')
		lis = ratelist.findAll('li', {'class':'J-ratelist-item rate-list__item cf'})
		for li in lis:
			review={}
			userblock=li.find('div',{'class':'user-info-block'}).find('p').findAll('span')
			review['name']=convert(userblock[0].get_text().strip())
			review['title']=convert(userblock[1].i.get('title'))
			
			review_block=li.find('div',{'class':'review-content-wrapper'})
			ratestars=str(review_block.find('div',{'class':'info cf'}).span.span.get('style'))
			review['star']=int(numreg.split(ratestars)[-2])/20	
		
			content=review_block.find('div',{'class':'J-normal-view'}).p.get_text().strip().split()[-1]
			review['content'] = convert(content)	
			reviews.append(review)
		merchant['review']=reviews;		
			
			
def test():
	url='http://localhost:8080/CMSSAnalysis/18620.html'
	merchant={}
	html=getHtml(url)
	crawleMerchantInfo(html, merchant)
	
	merchants=[]
	merchants.append(merchant)
	saveToJSON(merchants)	

def main():
	
	url='http://hz.meituan.com/category/meishi'
	merchants=[]

	while url is not None:
		html = getHtml(url)
		crawleMerchantsInfo(html, merchants)
		time.sleep(5)
		url = getNextPage(html)
		#url=None

	count=len(merchants)
	index=0
	for merchant in merchants:
		url=merchant['url']
		html=getHtml(url)
		try:
			print str(index)+'/'+str(count)
			crawleMerchantInfo(html, merchant)
			index=index+1
			time.sleep(10)
		except Exception, e:
			print e
			
	
	saveToJSON(merchants)

test()
