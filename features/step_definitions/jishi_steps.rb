# language: zh-CN

假如(/^用户打开应用$/) do
end

当(/^经过(\d+)秒$/) do |second|
    sleep second.to_i
end

那么(/^看见显示(\d+)秒$/) do |second|
    check_element_exists("* marked:'#{second}s'")
end