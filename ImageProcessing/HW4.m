function minMaxes = minMax(file)
    image = imread(file);
    image = im2bw(image);
    [x,y] = centroid(image);
    se = strel('disk', 1);
    smallIM = imerode(image, se);
    peri =  imabsdiff(image, smallIM);
    minMaxes = zeros(32,2);
    for i = 1:32
        minMaxes(i, 1) = 1000;
        minMaxes(i, 2) = 0;
    end
    [rows,cols] = size(peri);
    for col = 1:cols
        for row = 1:rows
            if(peri(row,col) == 1)
                angle = atan2(col-y,row-x);
                index = int(double(angle)/11.25);
                dist = sqrt((col-y)*(col-y)+(row-x)*(row-x));
                if(dist < minMaxes(index,1))
                    minMaxes(index,1) = dist;
                end
                if(dist > minMaxes(index,2))
                    minMaxes(index,2) = dist;
                end
            end
        end
    end