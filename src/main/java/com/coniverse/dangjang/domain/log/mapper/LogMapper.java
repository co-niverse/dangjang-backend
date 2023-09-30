package com.coniverse.dangjang.domain.log.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coniverse.dangjang.domain.log.dto.app.AppLog;
import com.coniverse.dangjang.domain.log.dto.request.LogRequest;
import com.coniverse.dangjang.domain.log.enums.Event;
import com.coniverse.dangjang.domain.user.entity.User;
import com.coniverse.dangjang.global.util.EnumFindUtil;

@Mapper(componentModel = "spring", imports = {EnumFindUtil.class, Event.class})
public interface LogMapper {
	@Mapping(target = "event", expression = "java(EnumFindUtil.findByTitle(Event.class, request.eventLogName()))")
	AppLog toAppLog(LogRequest request, User user);
}
