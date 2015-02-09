//
//  GrogCollectionCell.h
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@class ImageFetchRequest;
@interface GrogCollectionCell : UICollectionViewCell

@property (nonatomic, retain) UIImageView *icon;
@property (nonatomic, retain) UILabel *title;
@property (nonatomic, retain) ImageFetchRequest *request;
@property (nonatomic, retain) UIView *hoverView;
- (void)prepareWithTitle:(NSString *)t imagePath:(NSString *)imgPath selected:(BOOL)selected;
- (void)toggleHoverView:(BOOL)selected;
@end
