# Overview
This guide provides instructions to include the Funnke Developer Library (SDK) in your Android application so that you can add the Funnke Offerwall to your application and start earning money. It is important that you follow these steps and complete the requirements. The Developer Library includes the full features of the Funnke Advertiser SDK.

The SDK allows you to display our Offer Wall within your application. The Offer Wall displays Offers to end users. the Offers include a short description of the required action, and the rewards that users will receive for completing the action. The reward is calculated using the currency that is available within your App. This assumes that you offer a virtual currency within your app. This requirement is described in depth at the end of this document.

# Prerequisites
In order to integrate the SDK, you must complete the following prerequisites:
 1. Use the Eclipse IDE for building your Android application
 1. Have created an account at funnke.com
 1. Have registered an application for that account at http://partners.funnke.com
 1. Have a uniquely generated Application ID and Secret for your application
 1. Downloaded a copy of the current SDK available from https://github.com/funnkeadmin/funnkebeta

# Steps to Follow

 Step 1: Add the SDK library file to your project  
 Step 2: Update the Android manifest file for your application  
 Step 3: Make required updates to the Android project file for your application.  

### Step 1: Adding the SDK library file to your Eclipse project  
1. Open Eclipse, right-click on your project title, and select Import.  
1. In the Import dialog, select File System.  


![ScreenShot](https://raw.github.com/funnkeadmin/funnkebeta/master/images/image001.png)



1. Click Browse… next to the “From” directory field to locate ``` mrn-0.9.5.jar ``` from the downloaded SDK files.   
1. Click Browse… next to the “From” directory field to locate ``` age-1.1.7.jar ``` from the downloaded SDK files.  
1. Click Browse… next to the “From” directory field to locate ``` libGoogleAnalytics.jar ``` from the downloaded SDK files. If you already include the ``` libGoogleAnalytics.jar ``` library in your project you do not have to add it again  
1. Click Browse… next to the “Into folder” field to select a location for the files.
1. Click Finish and the *.jar files will appear in your Eclipse project window.  
1. Right-click on the ``` mrn-0.9.5.jar ```and select “Build Path...Add to Build Path”.  
1. Repeat step 8 for ``` age-1.1.7.jar ``` and ``` libGoogleAnalytics.jar ```.



![ScreenShot](https://raw.github.com/funnkeadmin/funnkebeta/master/images/image003.png)

### Step 2: Updating your Android manifest file.
The following permissions must be enabled in the ``` AndroidManifest.xml file ```. These permissions are enabled in order to allow the following important functions:  
 + Check whether the device is connected to the network.  
 + Access device identifiers which allow the code to recognize application installation events.  
 + Report the installation event.   
 + Attribute application installations and other events to the proper source (will not work without registering the referral receiver).  
 + Access user location for relevant localized offers.  
 + Send invitation SMS messages.  

![ScreenShot](https://raw.github.com/funnkeadmin/funnkebeta/master/images/image005.png)

The instructions below can be copied directly to your project.  

![ScreenShot](https://raw.github.com/funnkeadmin/funnkebeta/master/images/image007.png)

1. Double-click on the ``` AndroidManifest.xml ``` file in the open project area.  
1. Select the ``` Androidmanifest.xml ``` tab to view and edit.  
2. Add the following instruction into the ``` Androidmanifest.xml ``` file
  
``` 
<activity android:name="com.hookmobile.mrn.MrnWebView" ]]>  
  
  
</activity]]> 
```

1. Add the following list of permissions to the ``` AndroidManifest.xml file ```, either by typing them, or copying and pasting this list into the existing file: 

(Note: these instructions should be inserted after the user-sdk and before the application tag)

``` 
<uses-permission android:name= "android.permission.INTERNET" />
<uses-permission android:name= "android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name= "android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name= "android.permission.READ_PHONE_STATE" />
<uses-permission android:name= "android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name= "android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name= "android.permission.READ_CONTACTS" /> 
<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
```

#### Additional modification to ``` AndroidManifest.xml ``` file for referral tracking.  
Android OS supports the ability for a referral id to be passed through the appstore installation process and reveal the referring source of the installation. To properly configure this, you must update the ``` AndroidManifest.xml ``` to define Funnke’s Broadcast Receiver to capture and store the installation intent containing the referral information.   

Your app may already be taking advantage of this mechanism to track the effectiveness of ad campaigns. You can determine this by searching in your AndroidManifest.xml file for text: ``` com.android.vending.INSTALL_REFERRER ```. If you see something like the following, then your app is likely using this mechanism to track marketing activity that results in installs.  

```java
    <application>
      ..
        <receiver android:name= "com.some_url_for_tracking" android:exported= "true" >
            <intent-filter>
                <action android:name= "com.android.vending.INSTALL_REFERRER" />
            </intent-filter>
        </receiver>
    </application>
```

Note in above sample ``` AndroidManifest.xml ``` snippet, there is already a Broadcast Receiver ``` your.package.name.here.ReferralReceiver ``` defined to listen for the ``` INSTALL_REFERRER ``` intent. Funnke can co-exist with the existing Broadcast Receiver through daisy-chaining the Broadcast Receivers. Simply replace the above code block with following (changes highlighted in BOLD):

```java
    <application>
      ..
        <receiver android:name= "com.hookmobile.age.AgeBroadcast" android:exported= "true" >
            <intent-filter>
                <action android:name= "com.android.vending.INSTALL_REFERRER" />
            </intent-filter>
            <meta-data android:name=”newPackage” android:value=”your.package.name.here.ReferralReceiver” />
        </receiver>
    </application>
```

If your app does not currently track campaigns (i.e. you can't find any reference to ``` com.android.vending.INSTALL_REFERRER in AndroidManifest.xml``` ), then simply add the following code to your AndroidManifest.xml:

```java
    <application>
      ..
        <receiver android:name= "com.hookmobile.age.AgeBroadcast" android:exported= "true" >
            <intent-filter>
                <action android:name= "com.android.vending.INSTALL_REFERRER" />
            </intent-filter>
        </receiver>
    </application>
```


### Step 3: Making required updates to the Android project file.

The following list shows a summary of all required updates to your Android project. Each requirement is detailed within this section.  
 Required Update 1: Adding imports  
 Required Update 2: Activate ``` MrnManager ``` with assigned appKey and secret  
 Required Update 3: Creating a Session  
 Required Update 4: Displaying the Offer Wall  
R equired Update 5: Ending a Session  

#### Required Update 1: Adding imports  
Copy/paste the following into the java file where you plan to initialize the tracking activity.

```
import com.hookmobile.mrn.MrnManager;
```

#### Required Update 2: Activate the ``` MrnManager ``` 

The MrnManager contains both advertising/tracking and monetization functionalities. This class has methods needed to access all available features in the FunnkeSDK. It is important that your application connect to our service each time it is run. Doing so allows us to provide you with the fullest analytics and valid offers. You should invoke the ``` activate to theonCreate() ``` method.


Remember:

Call ``` MrnManager.activate() ``` by passing in the current activity (this) and assigned appKey and secret.

```
private static String MRN_APP_KEY = "your-assigned-app-key-here";
private static String MRN_SECRET = "your-assigned-secret-here";


@Override
protected void onCreate(Bundle savedInstanceState) {
  ..

  /*Activation of MrnManager class/  
  boolean debugging = true;
  boolean useVirtualNumber = true;
  MrnManager.activate(this, MRN_APP_KEY, MRN_SECRET, debugging, useVirtualNumber);

     ..
   }
```

 
#### Required Update 3: Creating a Session

Upon application launch, call the ``` MrnManager.getInstance().createSession() ``` in order to establish a session with Funnke. This method should be invoked in the ``` onStart() ``` method of your main activity window. This establishes a session with the Funnke servers and allows for reporting of daily active users in our system. 

```
@Override
public void onStart() {
	super.onStart();

	// *******************************************
	// * Track New Session *
	// *******************************************
	MrnManager.getInstance().createSession();
}

```
#### Required Update 4: Displaying the Offer Wall 

This update is required to launch the webview which requests and displays the offerwall.
``` 
MrnManager.getInstance().openOfferWall();				
```

The method will return a boolean which indicates if the Discovery Wall is to be shown or not.   

If openOfferWall() returns true then the Discovery Wall will open, if false then the Discovery Wall is not ready and will not be shown.   

The Discovery Wall fails if there is no session established by CreateSession operation or Internet connection is unavailable.   

_Need app screen shots here_  

#### Required Update 5: Ending a Session

It is important that you close the session when the user closes your application. Doing so is a best practice, and provides meaningful analytics for your application.   

```
@Override
**public** **void** onDestroy() {
	**super**.onDestroy();

	// *******************************************
	// * Release resources *
	// *******************************************
	MrnManager._getInstance_().release();
}
```		

## In Game Currency

Using this SDK to earn money using our Offer Wall assumes that you currently support an In-game currency within your application. USers view Offers and are rewarded whent hey complete the described action, typically, installing another application.

Once you have registered your application at Funnke.com, the system will automatically create a Placement for that App. The placement is how we identify your Offer Wall. You must configure that Placement with information about your virtual currency. Doing so allows us to calculate the reward that is due to a user for a compelted action. The Reward is calculated in your virtual currency. If your App features Gold Coins as in-game currency, your user will earn a relative number of gold coins for completing an action. The amount of gold coins is determined based on the rewards rate that you define, and the payout (bid) which the advertiser has asigned to a given Offer.

Once one of your users completes the action described in the offer, we will notify your server of the completed action, and the resulting reward to the end user. You are responsible to update the users balance with the appropriate number of your currency (for example, gold coins).

We use a web hook to notify you of the completed action and reward that is due to the end user.

To enable webhook, you will need to provision the callback URL for your app in the web portal.  Your server (at provisioned callback URL) must be able to handle HTTP POST request containing application/json data.  The JSON request body contains following parameters:


The webhook has the following syntax:

| Parameter       | Description                        | Syntax                                 | Example             |
|-----------------|------------------------------------|----------------------------------------|---------------------|
| type            | Type of event                      | Constant values: CREDIT                | CREDIT              |
| timestamp       | Timestamp of event                 | 'YYYY-MM-DD hh:mm:ss'                  | 2014-04-24 22:54:29 |
| virtualCurrency | Number of virtual currency awarded | Integer value                          | 20                  |
| androidId       | Android ID of user to be awarded   | String value. Empty for IOS device     | f8e81cf86096614d    |
| mac             | MAC Address of user to be awarded  | String value                           | 18:00:2d:df:c4:fe   |
| odin            | ODIN of user to be awarded         | String value                           | f8e81cf86096614d    |
| openUdid        | Open UDID of user to be awarded    | String value. Empty for Android device | 9ab7ccd32           |
| appKey          | Application Key                    | String value                           |                     |

Below is an example of the JSON HTTP POST body

```
{ 
"type":"CREDIT",
"timestamp":"2014-04-24 22:54:29",
"openUdid":null,
"virtualCurrency":"20",
"odin1":"f8e81cf86096614d",
"androidId":"f8e81cf86096614d",
"mac":"18:00:2d:df:c4:fe",
"appKey":"93CFAB5A70FA492E84CE47770EF040D6"
}
```

For those apps without the ability to integrate using our webhook we have developed an utility service that enables apps to use the SDK to query for pending rewards.

To enable this service you will need to provision the callback URL for your app in the web portal with the following URL http://rewardlog.funnke.com/funnkerewardmngr/api/rewards/

Once this is set up you will be able to use the SDK to query for pending users rewards.


#### Required Update 6: Reward users

Is recommended that you modify your app to reward users on app start, awake and when offer wall is closed.

App must use the SDK to get a list of pending rewards and iterate over the list to update the user balance with the appropriate number of your currency.  Once the reward has been redeemed the app must update the reward status to avoid rewarding the user twice for the same action.

Below a sample code of this process executed when the offer wall is closed.

``` 
if(MrnManager.getInstance().openOfferWall())
{
	Map<Integer, Integer> pendingRewards = MrnManager.getInstance().getPendingRewards();
	Iterator it = pendingRewards.entrySet().iterator();     
	while(it.hasNext()) {
		Map.Entry reward = (Map.Entry)it.next();
		Integer pointsToReward = (Integer) reward.getValue();
		//Add this point/coins to the user balance
		MrnManager.getInstance().completeReward((Integer) reward.getKey());
	}
	
}				
```
## UNITY 3D Plugin

We developed Unity3D plugin to provide an easy way to access the SDK functionality from Unity3D code.  It works pretty much like the native SDK underneath it, but thanks to Unity3D virtues this plugin really simplifies the setup and initialization.

### Prerequisites
In order to use the plugin, you must complete the following prerequisites:
 1. Have created an account at funnke.com
 1. Have registered an application for that account at http://partners.funnke.com
 1. Have a uniquely generated Application ID and Secret Key for your application
 1. Downloaded a copy of the plugin package available https://github.com/funnkeadmin/funnkebeta/blob/master/unityplugin/FunnkePlugin.unitypackage

### Steps to Follow
 1. Import the package to your project.
 1. Once imported you will be able to drag a prefab object called FunnkeManager, right into your scene's hierarchy.
 1. The prefab requires the Application ID and Secret Key provided by the Funnke web portal.  Enter your app keys or use these test keys:  App Key: 1B634D952BED4D64849A15DB4E072B3F Secret Key EF8FAA5CADBE4537B1F0EBA3611B3BB4
 ![ScreenShot](https://raw.github.com/funnkeadmin/funnkebeta/master/images/FunnkeUnity.png)
 1. And that's it, the FunnkeManager initializes it self at run time and creates a session, you just need to call the FunnkeManager methods as needed.

```
FunnkePlugin.Instance.OpenOfferWall();
FunnkePlugin.Reward[] rewards = FunnkePlugin.Instance.GetPendingRewards();
foreach (FunnkePlugin.Reward reward in rewards)
{
     //Award points to user
     FunnkePlugin.Instance.CompleteReward(reward.Key);
}
```
