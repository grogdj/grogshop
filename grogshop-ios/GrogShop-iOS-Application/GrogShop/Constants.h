//
//  Constants.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#ifndef GrogShop_Constants_h
#define GrogShop_Constants_h

#import "Utils.h"
#import "AppDelegate.h"

#define User_email @"user_email_id"
#define User_auth_token @"user_auth_token"
#define User_id @"user_id"

#define User_profile @"user_profile"

#define BG_COLOR [UIColor colorWithRed:235/255.0 green:235/255.0 blue:235/255.0 alpha:1.0]

#pragma mark networking url path extensions

#define kAuthRegister @"auth/register"
#define kAuthLogin @"auth/login"
#define kAuthLogout @"auth/logout"
#define kPublicAllInterests @"public/interests/all"
#define kAllClubs @"clubs/all"
#define kCacheDirName @"static_images"

static NSString *reusableIdentifier = @"GrogCell";

#endif
