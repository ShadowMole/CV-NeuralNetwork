function minMaxes = minMax()
    imnum = 1;
    for class = 0:3
        for num = 1:5
            
            filename = strcat(num2str(class),'_',num2str(num));
            image = imread(strcat('Images/', filename,'.png'));
            subplot(4,10,imnum);
            imshow(image, []);
            imnum = imnum + 1;
            image = ~(im2bw(image));
            img = regionprops(image);
            subplot(4,10,imnum);
            imshow(image, []);
            imnum = imnum + 1;
            % disp("filename: " + filename);
            centroid = img.Centroid;
            % disp(centroid);
           %disp("img: " + img);
            x = centroid(1);
            y = centroid(2);
            structElementSize = 50;
            se = strel('disk', structElementSize);
            smallIM = imerode(image, se);
            peri =  imabsdiff(image, smallIM);
            minMaxes = zeros(32,2);
            for i = 1:32
                minMaxes(i, 1) = 2000;
                minMaxes(i, 2) = 0;
            end
            [rows,cols] = size(peri);
            for col = 1:cols
                for row = 1:rows
                    if(peri(row,col) == 1)
%                         disp("y: " + y);
%                         disp("x: " + x);
                        % from 0 to 360 degrees
                        angle = atan2d(col-y,row-x) + 180;
                        %disp("angle: " + angle);
                        index = mod((int64(double(angle)/11.25)),32) +1;
                        %disp("index: " + index);
                        dist = sqrt((col-y).*(col-y)+(row-x).*(row-x));
                        if(dist < minMaxes(index,1))
                            minMaxes(index,1) = dist;
                        end
                        if(dist > minMaxes(index,2))
                            minMaxes(index,2) = dist;
                        end
                    end
                end
            end
            fid = fopen( strcat('Data/',filename,'.txt'), 'wt' );
            for J = 1 : 32
                for K = 1 : 2
                    if(minMaxes(J,K) == 0 || minMaxes(J,K) == 3000)
                        disp(strcat(filename,' ', J, ' ', K));
                    end
                    fprintf( fid, '%f,', minMaxes(J,K));
                end
            end
            fclose(fid);
        end
    end